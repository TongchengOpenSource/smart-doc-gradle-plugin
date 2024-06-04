package com.ly.doc.gradle.task;

import com.ly.doc.builder.javadoc.JavadocAdocBuilder;
import com.ly.doc.model.ApiConfig;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.logging.Logger;

public class JavadocAdocTask extends DocBaseTask{

    @Override
    public void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder, Logger logger) {
        try {
            JavadocAdocBuilder.buildApiDoc(apiConfig, javaProjectBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
