package com.smartdoc.gradle.plugin;

import com.smartdoc.gradle.task.PostmanTask;
import com.smartdoc.gradle.task.RestAdocTask;
import com.smartdoc.gradle.task.RestHtmlTask;
import com.smartdoc.gradle.task.RestMarkdownTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yu 2020/2/16.
 */
public class SmartDocPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        Map<String, Object> restHtml = new HashMap<>();
        restHtml.put("name", "smartDocRestHtml");
        restHtml.put("type", RestHtmlTask.class);
        restHtml.put("group", "Documentation");
        project.getTasks().create(restHtml);
        // create adoc
        Map<String, Object> restAdoc = new HashMap<>();
        restAdoc.put("name", "smartDocRestAdoc");
        restAdoc.put("type", RestAdocTask.class);
        restAdoc.put("group", "Documentation");
        project.getTasks().create(restAdoc);

        // create adoc
        Map<String, Object> restMarkdown = new HashMap<>();
        restMarkdown.put("name", "smartDocRestMarkdown");
        restMarkdown.put("type", RestMarkdownTask.class);
        restMarkdown.put("group", "Documentation");
        project.getTasks().create(restMarkdown);

        // create adoc
        Map<String, Object> restPostman = new HashMap<>();
        restPostman.put("name", "smartDocPostman");
        restPostman.put("type", PostmanTask.class);
        restPostman.put("group", "Documentation");
        project.getTasks().create(restPostman);
    }

}
