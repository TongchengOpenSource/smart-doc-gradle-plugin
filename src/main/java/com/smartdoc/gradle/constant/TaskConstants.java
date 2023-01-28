/*
 * smart-doc https://github.com/shalousun/smart-doc
 *
 * Copyright (C) 2018-2023 smart-doc
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
package com.smartdoc.gradle.constant;

import com.smartdoc.gradle.task.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2020/11/23.
 */
public class TaskConstants {

    /**
     * Map of Gradle Task
     */
    public static Map<String, Class> taskMap = new HashMap<>();

    static {
        //create html
        taskMap.put(GlobalConstants.REST_HTML_TASK, RestHtmlTask.class);
        // create adoc
        taskMap.put(GlobalConstants.REST_ADOC_TASK, RestAdocTask.class);
        // create markdown
        taskMap.put(GlobalConstants.REST_MARKDOWN_TASK, RestMarkdownTask.class);
        // create postman collection
        taskMap.put(GlobalConstants.POSTMAN_TASK, PostmanTask.class);
        // create open api
        taskMap.put(GlobalConstants.OPEN_API_TASK, OpenApiTask.class);
        //create rpc html
        taskMap.put(GlobalConstants.RPC_HTML_TASK, RpcHtmlTask.class);
        // create rpc adoc
        taskMap.put(GlobalConstants.RPC_ADOC_TASK, RpcAdocTask.class);
        // create rpc markdown
        taskMap.put(GlobalConstants.RPC_MARKDOWN_TASK, RpcMarkdownTask.class);
        // create torna rest
        taskMap.put(GlobalConstants.TORNA_REST_TASK,TornaRestTask.class);
        // create torna rpc
        taskMap.put(GlobalConstants.TORNA_RPC_TASK,TornaRpcTask.class);
    }
}
