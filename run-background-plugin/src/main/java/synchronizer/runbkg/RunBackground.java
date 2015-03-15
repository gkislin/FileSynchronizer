package synchronizer.runbkg;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import java.util.HashSet;
import java.util.Set;

/**
 * GKislin
 * 13.03.2015.
 *
 * @link http://maven.apache.org/guides/mini/guide-maven-classloading.htm
 */
@Mojo(name = "run", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class RunBackground extends AbstractMojo {

    @Parameter(required = true, property = "mainClass")
    private String mainClass;

    @Component
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            //TODO implement for unix
            System.out.println("Run " + mainClass + " in background");
            Set<String> classpath = new HashSet<>(project.getCompileClasspathElements());
            classpath.addAll(project.getRuntimeClasspathElements());
//            Runtime.getRuntime().exec("cmd /c start java -cp " +
//                    String.join(System.getProperty("path.separator"), classpath) + ' ' + mainClass);
            new ProcessBuilder("java", "-cp", String.join(System.getProperty("path.separator"), classpath), mainClass).start();
        } catch (Exception e) {
            throw new MojoExecutionException("Execution failed", e);
        }
    }
}