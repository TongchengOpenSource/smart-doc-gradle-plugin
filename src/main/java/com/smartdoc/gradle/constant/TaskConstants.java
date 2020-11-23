package com.smartdoc.gradle.constant;

import com.smartdoc.gradle.task.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2020/11/23.
 */
public class TaskConstants {
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
    }
}
