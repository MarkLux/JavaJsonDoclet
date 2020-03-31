package com.marklux.doclet.plugin;

import com.marklux.doclet.JsonDoclet;
import com.sun.tools.javadoc.Main;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.*;

@Mojo(name = "package",defaultPhase = LifecyclePhase.PACKAGE)
public class PackageMojo extends AbstractMojo {

    /**
     * schema 文件名
     */
    private static final String SCHEMA_FILE_NAME = "domain-schema.json";

    /**
     * 源码位置，默认是对应模块的文件目录
     */
    @Parameter(property = "sourcePath")
    private String sourcePath;

    /**
     * 指定源码包，默认是顶级包名：com
     */
    @Parameter(property = "subPackage", defaultValue = "com")
    private String subPackage;

    /**
     * 指定编码，默认为UTF-8
     */
    @Parameter(property = "encoding", defaultValue = "UTF-8")
    private String encoding;

    /**
     * classpath,默认当前目录
     */
    @Parameter(property = "classpath", defaultValue = ".")
    private String classPath;

    @Parameter(property = "outputpath", defaultValue = "./")
    private String outputPath;

    @Component
    private ProjectBuilder projectBuilder;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("start to execute model schema package, params: {sourcepath: " + sourcePath + ", subpackage: " + subPackage + "}");
        StringBuilder classBuilder = new StringBuilder();
        // 拼装maven的classpath，用于加载完整的lib
        System.currentTimeMillis();
        for (Artifact dependencyArtifact : project.getDependencyArtifacts()) {
            classBuilder.append(dependencyArtifact.getFile()).append(":");
//            System.out.println(dependencyArtifact.getArtifactId());
//            System.out.println(dependencyArtifact.getFile());
        }
        // 执行doclet

        System.out.println(classBuilder.toString());

        Main.execute("test", JsonDoclet.class.getName(), Thread.currentThread().getContextClassLoader(),
                "-sourcepath", sourcePath,
                "-subpackages", subPackage,
                "-encoding", encoding,
                "-outputfile", outputPath + SCHEMA_FILE_NAME,
                "-classpath", classBuilder.toString());
    }
}
