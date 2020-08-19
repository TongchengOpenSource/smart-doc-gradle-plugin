/*
 * smart-doc https://github.com/shalousun/smart-doc
 *
 * Copyright (C) 2018-2020 smart-doc
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
package com.smartdoc.gradle.plugin;

import com.smartdoc.gradle.constant.GlobalConstants;
import com.smartdoc.gradle.extension.SmartDocPluginExtension;
import com.smartdoc.gradle.task.*;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;

/**
 * @author yu 2020/2/16.
 */
public class SmartDocPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        Task javaCompileTask = project.getTasks().getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME);
        //create html
        RestHtmlTask restHtmlTask = project.getTasks().create(GlobalConstants.REST_HTML_TASK, RestHtmlTask.class);
        restHtmlTask.setGroup(GlobalConstants.TASK_GROUP);
        restHtmlTask.dependsOn(javaCompileTask);

        // create adoc
        RestAdocTask restAdocTask = project.getTasks().create(GlobalConstants.REST_ADOC_TASK, RestAdocTask.class);
        restAdocTask.setGroup(GlobalConstants.TASK_GROUP);
        restAdocTask.dependsOn(javaCompileTask);

        // create markdown
        RestMarkdownTask restMarkdownTask = project.getTasks().create(GlobalConstants.REST_MARKDOWN_TASK, RestMarkdownTask.class);
        restMarkdownTask.setGroup(GlobalConstants.TASK_GROUP);
        restMarkdownTask.dependsOn(javaCompileTask);

        // create postman collection
        PostmanTask postmanTask = project.getTasks().create(GlobalConstants.POSTMAN_TASK, PostmanTask.class);
        postmanTask.setGroup(GlobalConstants.TASK_GROUP);
        postmanTask.dependsOn(javaCompileTask);

        // create open api
        OpenApiTask openApiTask = project.getTasks().create(GlobalConstants.OPEN_API_TASK, OpenApiTask.class);
        openApiTask.setGroup(GlobalConstants.TASK_GROUP);
        openApiTask.dependsOn(javaCompileTask);

        //create rpc html
        RpcHtmlTask rpcHtmlTask = project.getTasks().create(GlobalConstants.RPC_HTML_TASK, RpcHtmlTask.class);
        rpcHtmlTask.setGroup(GlobalConstants.TASK_GROUP);
        rpcHtmlTask.dependsOn(javaCompileTask);

        // create rpc adoc
        RpcAdocTask rpcAdocTask = project.getTasks().create(GlobalConstants.RPC_ADOC_TASK, RpcAdocTask.class);
        rpcAdocTask.setGroup(GlobalConstants.TASK_GROUP);
        rpcAdocTask.dependsOn(javaCompileTask);

        // create rpc markdown
        RpcMarkdownTask rpcMarkdownTask = project.getTasks().create(GlobalConstants.RPC_MARKDOWN_TASK, RpcMarkdownTask.class);
        rpcMarkdownTask.setGroup(GlobalConstants.TASK_GROUP);
        rpcMarkdownTask.dependsOn(javaCompileTask);

        // extend project-model to get our settings/configuration via nice configuration
        project.getExtensions().create(GlobalConstants.EXTENSION_NAME, SmartDocPluginExtension.class);
    }

}
