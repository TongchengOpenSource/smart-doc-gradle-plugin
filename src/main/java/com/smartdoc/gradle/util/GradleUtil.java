package com.smartdoc.gradle.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.power.common.util.FileUtil;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiDataDictionary;
import com.power.doc.model.ApiErrorCodeDictionary;
import com.power.doc.model.SourceCodePath;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

/**
 * @author yu 2020/2/16.
 */
public class GradleUtil {
    /**
     * Gson Object
     */
    public final static Gson GSON = new GsonBuilder().addDeserializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }).create();

    /**
     * Build ApiConfig
     *
     * @param configFile  config file
     * @param project     Project object
     * @param log         gradle plugin log
     * @return com.power.doc.model.ApiConfig
     */
    public static ApiConfig buildConfig(File configFile, Project project, Logger log)  {
        try {
            ClassLoader classLoader = ClassLoaderUtil.getRuntimeClassLoader(project);
            String data = FileUtil.getFileContent(new FileInputStream(configFile));
            ApiConfig apiConfig = GSON.fromJson(data, ApiConfig.class);
            List<ApiDataDictionary> apiDataDictionaries = apiConfig.getDataDictionaries();
            List<ApiErrorCodeDictionary> apiErrorCodes = apiConfig.getErrorCodeDictionaries();
            if (apiErrorCodes != null) {
                apiErrorCodes.forEach(
                        apiErrorCode -> {
                            String className = apiErrorCode.getEnumClassName();
                            apiErrorCode.setEnumClass(getClassByClassName(className, classLoader));
                        }
                );
            }
            if (apiDataDictionaries != null) {
                apiDataDictionaries.forEach(
                        apiDataDictionary -> {
                            String className = apiDataDictionary.getEnumClassName();
                            apiDataDictionary.setEnumClass(getClassByClassName(className, classLoader));
                        }
                );
            }
            addSourcePaths(project, apiConfig, log);
            return apiConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 根据 com.xxx.AClass获取类Class
     *
     * @param className   类名
     * @param classLoader urls
     * @return className
     */
    public static Class getClassByClassName(String className, ClassLoader classLoader) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void addSourcePaths(Project project, ApiConfig apiConfig, Logger log) {
        Set<Project> sourceRoots = project.getAllprojects();
        sourceRoots.forEach(s -> {
            log.info("path",s.getPath());
            apiConfig.setSourceCodePaths(SourceCodePath.path().setPath(s.getPath()));
        });
        if (Objects.nonNull(project.getParent())) {
            Project mavenProject = project.getParent();
            if (null != mavenProject) {
                log.info("--- parent project name is [" + mavenProject.getName() + "]");
                File file = mavenProject.getProjectDir();
                if (!Objects.isNull(file)) {
                    log.info("--- parent project basedir is " + file.getPath());
                    apiConfig.setSourceCodePaths(SourceCodePath.path().setPath(file.getPath()));
//                    log.info("--- smart-doc-maven-plugin loaded resource from " + file.getPath());
                }
            }
        }
    }
}
