/*
 * smart-doc https://github.com/shalousun/smart-doc
 *
 * Copyright (C) 2019-2020 smart-doc
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

import com.google.gson.Gson;
import com.power.common.constants.Charset;
import com.power.doc.model.ApiConfig;
import com.smartdoc.gradle.constant.GlobalConstants;
import com.smartdoc.gradle.plugin.SmartDocPluginExtension;
import com.smartdoc.gradle.util.GradleUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.component.Artifact;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author yu 2020/4/5.
 */
public abstract class DocBaseTask extends DefaultTask {

    protected JavaProjectBuilder javaProjectBuilder;

    public abstract void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger);

    @TaskAction
    public void action() {
        Logger logger = getLogger();
        logger.quiet("this is baseTask");
        Project project = getProject();

        logger.quiet("Smart-doc Starting Create API Documentation.");
        javaProjectBuilder = buildJavaProjectBuilder(project);
        javaProjectBuilder.setEncoding(Charset.DEFAULT_CHARSET);
        SmartDocPluginExtension pluginExtension = project.getExtensions().getByType(SmartDocPluginExtension.class);
        File file = pluginExtension.getConfigFile();
        if (Objects.isNull(file)) {
            file = new File(GlobalConstants.DEFAULT_CONFIG);
        }
        ApiConfig apiConfig = GradleUtil.buildConfig(file, project, logger);
        Gson gson = new Gson();
        System.out.println(gson.toJson(apiConfig));
        if (apiConfig == null) {
            logger.quiet(GlobalConstants.ERROR_MSG);
            return;
        }
        Path path = Paths.get(apiConfig.getOutPath());
        if (!path.isAbsolute()) {
            apiConfig.setOutPath(project.getPath() + "/" + apiConfig.getOutPath());
            logger.quiet("API Documentation output to " + apiConfig.getOutPath());
        } else {
            logger.quiet("API Documentation output to " + apiConfig.getOutPath());
        }

        this.executeAction(apiConfig, javaProjectBuilder, logger);
    }


    /**
     * Classloading
     *
     * @return
     */
    private JavaProjectBuilder buildJavaProjectBuilder(Project project) {
        JavaProjectBuilder javaDocBuilder = new JavaProjectBuilder();
        javaDocBuilder.setEncoding(Charset.DEFAULT_CHARSET);
        javaDocBuilder.setErrorHandler(e -> getLogger().warn(e.getMessage()));
        //addSourceTree
        javaDocBuilder.addSourceTree(new File("src/main/java"));
        //sources.stream().map(File::new).forEach(javaDocBuilder::addSourceTree);
//        javaDocBuilder.addClassLoader(ClassLoaderUtil.getRuntimeClassLoader(project));
        loadSourcesDependencies(javaDocBuilder,project);
        return javaDocBuilder;
    }

    /**
     * load sources
     *
     * @param javaDocBuilder
     */
    private void loadSourcesDependencies(JavaProjectBuilder javaDocBuilder,Project project) {
        DependencyHandler dependencyHandler = project.getDependencies();
        Configuration compileConfiguration = project.getConfigurations().getByName("compile");
        compileConfiguration.getResolvedConfiguration().getResolvedArtifacts().forEach(resolvedArtifact -> {
            ModuleVersionIdentifier id = resolvedArtifact.getModuleVersion().getId();
            System.out.println(id.getGroup()+":"+id.getName()+":"+id.getVersion()+":sources");
        });
        FileCollection fileCollection = compileConfiguration.getAllArtifacts().getFiles();
        fileCollection.getFiles().forEach(file -> {
            System.out.println("打印依赖："+file.getName());
        });
        ArtifactHandler artifactHandler = project.getArtifacts();


    }

    /**
     * reference https://github.com/sfauvel/livingdocumentation
     *
     * @param javaDocBuilder  JavaProjectBuilder
     * @param sourcesArtifact Artifact
     */
    private void loadSourcesDependency(JavaProjectBuilder javaDocBuilder, Artifact sourcesArtifact) {

    }


}
