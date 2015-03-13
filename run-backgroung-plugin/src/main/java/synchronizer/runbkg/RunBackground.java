package synchronizer.runbkg;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import java.util.StringJoiner;

/**
 * GKislin
 * 13.03.2015.
 */
@Mojo(name = "run", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class RunBackground extends AbstractMojo {

    @Parameter
    private String mainClass;

    @Component
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        try {
            System.out.println("Run " + mainClass + " in background");
            System.out.println();
            // or mark as
            Runtime.getRuntime().exec("cmd /c start java -cp " + StringJoiner(System.getProperty("path.separator")) + ' ' + mainClass);
        } catch (Exception e) {
            throw new MojoExecutionException("Execution failed", e);
        }
    }
}