package com.smartdoc.gradle.task;

import com.power.doc.builder.YapiBuilder;
import com.power.doc.model.ApiConfig;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.logging.Logger;

public class YapiRestTask extends DocBaseTask {

    @Override
    public void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger) {
        try {
            YapiBuilder.buildApiDoc(apiConfig, javaProjectBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
