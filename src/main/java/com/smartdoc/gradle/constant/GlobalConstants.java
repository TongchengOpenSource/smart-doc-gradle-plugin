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
package com.smartdoc.gradle.constant;

/**
 * @author yu 2019/12/13.
 */
public interface GlobalConstants {

    String ERROR_MSG = "Failed to build ApiConfig, check if the configuration file is correct.";

    String DEFAULT_CONFIG = "./src/main/resources/default.json";

    String TASK_GROUP = "Documentation";

    String REST_HTML_TASK = "smartDocRestHtml";

    String REST_ADOC_TASK = "smartDocRestAdoc";

    String REST_MARKDOWN_TASK = "smartDocRestMarkdown";

    String POSTMAN_TASK = "smartDocPostman";

    String OPEN_API_TASK = "smartDocOpenApi";

    String RPC_HTML_TASK = "smartDocRpcHtml";

    String RPC_ADOC_TASK = "smartDocRpcAdoc";

    String RPC_MARKDOWN_TASK = "smartDocRpcMarkdown";

    String TORNA_REST_TASK = "tornaRest";

    String TORNA_RPC_TASK = "tornaRpc";

    String YAPI_REST_TASK = "yapiRest";

    String EXTENSION_NAME = "smartdoc";

    String SRC_MAIN_JAVA_PATH = "src/main/java";

}
