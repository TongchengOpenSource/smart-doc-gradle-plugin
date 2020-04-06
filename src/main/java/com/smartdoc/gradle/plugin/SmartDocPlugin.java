package com.smartdoc.gradle.plugin;

import com.smartdoc.gradle.constant.GlobalConstants;
import com.smartdoc.gradle.task.PostmanTask;
import com.smartdoc.gradle.task.RestAdocTask;
import com.smartdoc.gradle.task.RestHtmlTask;
import com.smartdoc.gradle.task.RestMarkdownTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author yu 2020/2/16.
 */
public class SmartDocPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        //create html
        RestHtmlTask restHtmlTask = project.getTasks().create(GlobalConstants.REST_HTML_TASK, RestHtmlTask.class);
        restHtmlTask.setGroup(GlobalConstants.TASK_GROUP);

        // create adoc
        RestAdocTask restAdocTask = project.getTasks().create(GlobalConstants.REST_ADOC_TASK, RestAdocTask.class);
        restAdocTask.setGroup(GlobalConstants.TASK_GROUP);

        // create markdown
        RestMarkdownTask restMarkdownTask = project.getTasks().create(GlobalConstants.REST_MARKDOWN_TASK, RestMarkdownTask.class);
        restMarkdownTask.setGroup(GlobalConstants.TASK_GROUP);

        // create postman collection
        PostmanTask postmanTask = project.getTasks().create(GlobalConstants.POSTMAN_TASK, PostmanTask.class);
        postmanTask.setGroup(GlobalConstants.TASK_GROUP);
    }

}
