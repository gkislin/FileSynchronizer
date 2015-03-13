package synchronizer.common.util;

import java.io.IOException;

/**
 * GKislin
 * 13.03.2015.
 */
public class RunBackground {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("java.class.path"));
        // http://maven.apache.org/guides/mini/guide-maven-classloading.html
        // Need custom maven plugin to get project classpath from MavenProject
        // or mark as @requiresDependencyResolution
        Runtime.getRuntime().exec("cmd /c start java -classpass " + System.getProperty("java.class.path") + ' ' + args[0]);
    }
}
