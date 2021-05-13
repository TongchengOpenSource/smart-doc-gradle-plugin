/*
 * smart-doc https://github.com/shalousun/smart-doc
 *
 * Copyright (C) 2018-2021 smart-doc
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.smartdoc.gradle.task;

import com.power.common.constants.Charset;
import com.power.common.util.RegexUtil;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.model.ApiConfig;
import com.smartdoc.gradle.constant.GlobalConstants;
import com.smartdoc.gradle.extension.SmartDocPluginExtension;
import com.smartdoc.gradle.model.CustomArtifact;
import com.smartdoc.gradle.util.ArtifactFilterUtil;
import com.smartdoc.gradle.util.GradleUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.artifacts.component.ComponentIdentifier;
import org.gradle.api.artifacts.result.ArtifactResult;
import org.gradle.api.artifacts.result.ComponentArtifactsResult;
import org.gradle.api.internal.artifacts.result.DefaultResolvedArtifactResult;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskAction;
import org.gradle.jvm.JvmLibrary;
import org.gradle.language.base.artifact.SourcesArtifact;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author yu 2020/4/5.
 */
public abstract class DocBaseTask extends DefaultTask {

    protected JavaProjectBuilder javaProjectBuilder;

    private static String MSG = "The loaded local code path is ";

    public abstract void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger);

    @TaskAction
    public void action() {
        Logger logger = getLogger();
        Project project = getProject();
        logger.quiet("Smart-doc Starting Create API Documentation.");
        SmartDocPluginExtension pluginExtension = project.getExtensions().getByType(SmartDocPluginExtension.class);
        Set<String> excludes = pluginExtension.getExclude();
        Set<String> includes = pluginExtension.getInclude();
        javaProjectBuilder = buildJavaProjectBuilder(project, excludes, includes);
        javaProjectBuilder.setEncoding(Charset.DEFAULT_CHARSET);
        File file = pluginExtension.getConfigFile();
        if (Objects.isNull(file)) {
            file = new File(GlobalConstants.DEFAULT_CONFIG);
        }
        ApiConfig apiConfig = GradleUtil.buildConfig(file, project, logger);
        if (apiConfig == null) {
            logger.quiet(GlobalConstants.ERROR_MSG);
            return;
        }
        if (Objects.nonNull(apiConfig.getRpcConsumerConfig())) {
            Path rpcConsumerPath = Paths.get(apiConfig.getRpcConsumerConfig());
            if (!rpcConsumerPath.isAbsolute()) {
                apiConfig.setRpcConsumerConfig(project.getProjectDir().getPath() + "/" + apiConfig.getRpcConsumerConfig());
            }
        }
        Path path = Paths.get(apiConfig.getOutPath());
        if (!path.isAbsolute()) {
            apiConfig.setOutPath(project.getProjectDir().getPath() + "/" + apiConfig.getOutPath());
        }
        logger.quiet("API Documentation output to " + apiConfig.getOutPath());
        this.executeAction(apiConfig, javaProjectBuilder, logger);
    }


    /**
     * Classloading
     *
     * @return
     */
    private JavaProjectBuilder buildJavaProjectBuilder(Project project, Set<String> excludes, Set<String> includes) {
        JavaProjectBuilder javaDocBuilder = new JavaProjectBuilder();
        javaDocBuilder.setEncoding(Charset.DEFAULT_CHARSET);
        javaDocBuilder.setErrorHandler(e -> getLogger().warn(e.getMessage()));
        //addSourceTree
        String projectDir = project.getProjectDir().getPath();
        String projectCodePath = String.join(DocGlobalConstants.FILE_SEPARATOR, projectDir, DocGlobalConstants.PROJECT_CODE_PATH);
        getLogger().quiet(MSG + projectCodePath);
        javaDocBuilder.addSourceTree(new File(projectCodePath));
        //sources.stream().map(File::new).forEach(javaDocBuilder::addSourceTree);
//        javaDocBuilder.addClassLoader(ClassLoaderUtil.getRuntimeClassLoader(project));
        loadSourcesDependencies(javaDocBuilder, project, excludes, includes);
        return javaDocBuilder;
    }

    /**
     * load sources
     *
     * @param javaDocBuilder
     */
    private void loadSourcesDependencies(JavaProjectBuilder javaDocBuilder, Project project, Set<String> excludes, Set<String> includes) {
        Configuration compileConfiguration = project.getConfigurations().getByName(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME);
        List<ComponentIdentifier> binaryDependencies = new ArrayList<>();
        TreeMap<String, Project> allModules = this.getAllModule(project.getRootProject());
        Set<ResolvedArtifact> resolvedArtifacts = compileConfiguration.getResolvedConfiguration().getResolvedArtifacts();
        for (ResolvedArtifact resolvedArtifact : resolvedArtifacts) {
            String displayName = resolvedArtifact.getId().getComponentIdentifier().getDisplayName();
            CustomArtifact moduleArtifact = null;
            boolean selfModule = displayName.startsWith("project :");
            if (selfModule) {
                ResolvedModuleVersion version = resolvedArtifact.getModuleVersion();
                moduleArtifact = CustomArtifact.builder().setGroup(version.getId().getGroup())
                        .setArtifactId(version.getId().getName())
                        .setVersion(version.getId().getVersion());
                // add local source
                String artifactName = moduleArtifact.getGroup() + ":" + moduleArtifact.getArtifactId();
                addModuleSourceTree(javaDocBuilder, allModules, artifactName);

            }
            CustomArtifact artifact = selfModule ? moduleArtifact : CustomArtifact.builder(displayName);
            if (ArtifactFilterUtil.ignoreArtifact(artifact) || ArtifactFilterUtil.ignoreSpringBootArtifactById(artifact)) {
                continue;
            }
            String artifactName = artifact.getGroup() + ":" + artifact.getArtifactId();
            if (RegexUtil.isMatches(excludes, artifactName)) {
                continue;
            }
            if (RegexUtil.isMatches(includes, artifactName)) {
                if (selfModule) {
                    addModuleSourceTree(javaDocBuilder, allModules, displayName);
                    continue;
                }
                binaryDependencies.add(resolvedArtifact.getId().getComponentIdentifier());
                continue;
            }
            if (includes.size() < 1 && !selfModule) {
                binaryDependencies.add(resolvedArtifact.getId().getComponentIdentifier());
            }
        }
        Set<ComponentArtifactsResult> artifactsResults = project.getDependencies().createArtifactResolutionQuery()
                .forComponents(binaryDependencies)
                .withArtifacts(JvmLibrary.class, SourcesArtifact.class)
                .execute()
                .getResolvedComponents();
        for (ComponentArtifactsResult artifactResult : artifactsResults) {
            for (ArtifactResult sourcesResult : artifactResult.getArtifacts(SourcesArtifact.class)) {
                if (sourcesResult instanceof DefaultResolvedArtifactResult) {
                    this.loadSourcesDependency(javaDocBuilder, (DefaultResolvedArtifactResult) sourcesResult);
                }
            }
        }
    }

    /**
     * reference https://github.com/sfauvel/livingdocumentation
     *
     * @param javaDocBuilder JavaProjectBuilder
     * @param artifact       Artifact
     */
    private void loadSourcesDependency(JavaProjectBuilder javaDocBuilder, DefaultResolvedArtifactResult artifact) {
        try (JarFile jarFile = new JarFile(artifact.getFile())) {
            for (Enumeration<?> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = (JarEntry) entries.nextElement();
                String name = entry.getName();
                if (name.endsWith(".java") && !name.endsWith("/package-info.java")) {
                    javaDocBuilder.addSource(
                            new URL("jar:" + artifact.getFile().toURI().toURL().toString() + "!/" + name));
                }
            }
        } catch (Exception e) {
            getLogger().warn("Unable to load jar source " + artifact + " : " + e.getMessage());
        }
    }

    private void addModuleSourceTree(JavaProjectBuilder javaDocBuilder, TreeMap<String, Project> allModules, String artifactName) {
        Project module = allModules.getOrDefault(artifactName, null);
        if (module != null) {
            String modelSrc = String.join(File.separator, module.getProjectDir().getAbsolutePath(), GlobalConstants.SRC_MAIN_JAVA_PATH);
            getLogger().quiet(MSG + modelSrc);
            javaDocBuilder.addSourceTree(new File(modelSrc));
        }
    }

    private TreeMap<String, Project> getAllModule(Project rootProject) {
        TreeMap<String, Project> result = new TreeMap<>();
        if (Objects.isNull(rootProject)) {
            return result;
        }
        if (rootProject.getDepth() != 0) {
            result.put(rootProject.getGroup() + ":" + rootProject.getName(), rootProject);
        }
        if (rootProject.getChildProjects().isEmpty()) {
            return result;
        }
        rootProject.getChildProjects().forEach((k, v) -> result.putAll(this.getAllModule(v)));
        return result;
    }
}
