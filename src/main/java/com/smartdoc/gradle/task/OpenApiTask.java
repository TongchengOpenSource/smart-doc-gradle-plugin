package com.smartdoc.gradle.task;

import com.power.doc.builder.OpenApiBuilder;
import com.power.doc.model.ApiConfig;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.logging.Logger;

/**
 * Support OpenApi 3.0+
 * @author yu 2020/8/20.
 */
public class OpenApiTask extends DocBaseTask {

    @Override
    public void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger) {
        try {
            OpenApiBuilder.buildOpenApi(apiConfig, javaProjectBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
