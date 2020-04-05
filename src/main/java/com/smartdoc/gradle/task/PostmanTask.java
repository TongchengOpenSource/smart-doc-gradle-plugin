package com.smartdoc.gradle.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * @author yu 2020/4/5.
 */
public class PostmanTask extends DefaultTask {

    @TaskAction
    public void action() {
        System.out.println("执行生成postman collection");
    }
}
