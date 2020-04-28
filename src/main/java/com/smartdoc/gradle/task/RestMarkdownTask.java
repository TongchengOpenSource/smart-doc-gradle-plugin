package com.smartdoc.gradle.task;

import com.power.doc.builder.ApiDocBuilder;
import com.power.doc.model.ApiConfig;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.logging.Logger;

/**
 * @author yu 2020/4/5.
 */
public class RestMarkdownTask extends DocBaseTask {

    @Override
    public void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger) {
        try {
            logger.quiet("This is markdown task");
            ApiDocBuilder.buildApiDoc(apiConfig, javaProjectBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
