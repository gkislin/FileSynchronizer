package synchronizer.runbkg;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

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
            System.out.println("Run " + mainClass + " in background");
            Runtime.getRuntime().exec("cmd /c start java -cp " +
                    String.join(System.getProperty("path.separator"), project.getCompileClasspathElements()) + ' ' + mainClass);
        } catch (Exception e) {
            throw new MojoExecutionException("Execution failed", e);
        }
    }
}