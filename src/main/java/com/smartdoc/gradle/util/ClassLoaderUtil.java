/*
 * Living Documentation
 *
 * Copyright (C) 2017 Focus IT
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
package com.smartdoc.gradle.util;


import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author Julien Boz
 */
public class ClassLoaderUtil {

    /**
     * Get classloader
     *
     * @param project MavenProject
     * @return ClassLoader
     */
    public static ClassLoader getRuntimeClassLoader(Project project)  {
        try {
            Configuration compileConfiguration = project.getConfigurations().getByName("compile");
            Set<File> fileSet = compileConfiguration.getFiles();
            List<URL> urls = new ArrayList<>();
            for(File file:fileSet){
                urls.add(file.toURI().toURL());
            }
            URL[] runtimeUrls = urls.toArray(new URL[0]);
            return new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to load project runtime !", e);
        }
    }
}
