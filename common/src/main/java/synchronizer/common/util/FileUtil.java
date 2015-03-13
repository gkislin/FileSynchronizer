package synchronizer.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * GKislin
 * 14.03.2015.
 */
public class FileUtil {

    public static void main(String[] args) throws IOException {
        replaceExtension(Paths.get("xml_storage\\receiving\\chunk_user4-user6.bad"), "xml");
    }

    public static void addExtension(Path path, String ext) throws IOException {
        Files.move(path, Paths.get(path.toString() + '.' + ext));
    }

    public static void replaceExtension(Path path, String ext) throws IOException {
        Files.move(path, path.resolveSibling(getNameWithoutExtension(path) + '.' + ext));
    }

    public static String getNameWithoutExtension(Path path) {
        return com.google.common.io.Files.getNameWithoutExtension(path.toString());
    }
}
