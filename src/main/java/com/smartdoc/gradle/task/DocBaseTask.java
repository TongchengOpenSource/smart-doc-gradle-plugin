package com.smartdoc.gradle.task;

import com.power.common.constants.Charset;
import com.power.doc.model.ApiConfig;
import com.smartdoc.gradle.constant.GlobalConstants;
import com.smartdoc.gradle.util.ClassLoaderUtil;
import com.smartdoc.gradle.util.GradleUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.component.Artifact;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yu 2020/4/5.
 */
public abstract class DocBaseTask extends DefaultTask {

    private File configFile;

    protected JavaProjectBuilder javaProjectBuilder;

    public abstract void executeAction(ApiConfig apiConfig, JavaProjectBuilder javaProjectBuilder,Logger logger);

    @TaskAction
    public void action(){
        Logger logger = getLogger();
        logger.quiet("this is baseTask");
//        Project project = getProject();
//        Logger logger = getLogger();
//        logger.info("Smart-doc Starting Create API Documentation.");
//        javaProjectBuilder = buildJavaProjectBuilder(project);
//        javaProjectBuilder.setEncoding(Charset.DEFAULT_CHARSET);
//        ApiConfig apiConfig = GradleUtil.buildConfig(configFile, project, logger);
//        if (apiConfig == null) {
//            logger.info(GlobalConstants.ERROR_MSG);
//            return;
//        }
//        Path path = Paths.get(apiConfig.getOutPath());
//        if (!path.isAbsolute()) {
//            apiConfig.setOutPath(project.getPath() + "/" + apiConfig.getOutPath());
//            logger.info("API Documentation output to " + apiConfig.getOutPath());
//        } else {
//            logger.info("API Documentation output to " + apiConfig.getOutPath());
//        }
        ApiConfig apiConfig = new ApiConfig();

        this.executeAction(apiConfig, javaProjectBuilder,logger);
    }



    /**
     * Classloading
     *
     * @return
     */
    private JavaProjectBuilder buildJavaProjectBuilder(Project project) {
        JavaProjectBuilder javaDocBuilder = new JavaProjectBuilder();
        javaDocBuilder.setEncoding(Charset.DEFAULT_CHARSET);
        javaDocBuilder.setErrorHandler(e -> getLogger().warn(e.getMessage()));
        //addSourceTree
        javaDocBuilder.addSourceTree(new File("src/main/java"));
        //sources.stream().map(File::new).forEach(javaDocBuilder::addSourceTree);
        javaDocBuilder.addClassLoader(ClassLoaderUtil.getRuntimeClassLoader(project));
        loadSourcesDependencies(javaDocBuilder);
        return javaDocBuilder;
    }

    /**
     * load sources
     *
     * @param javaDocBuilder
     */
    private void loadSourcesDependencies(JavaProjectBuilder javaDocBuilder) {

    }

    /**
     * reference https://github.com/sfauvel/livingdocumentation
     *
     * @param javaDocBuilder  JavaProjectBuilder
     * @param sourcesArtifact Artifact
     */
    private void loadSourcesDependency(JavaProjectBuilder javaDocBuilder, Artifact sourcesArtifact) {

    }


}
