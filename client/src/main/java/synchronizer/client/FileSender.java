package synchronizer.client;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * GKislin
 * 05.03.2015.
 * <p/>
 * TODO implement
 * Send by network
 * Mock for test: just copy to other dir with overriding
 */
public class FileSender {

    public Writer getWriter(String path) throws IOException {
        Path targetFile = ClientConfig.get().getTargetDirectory().resolve(path);
        if (!Files.exists(targetFile)) {
            return Files.newBufferedWriter(targetFile, StandardCharsets.UTF_8);
        }
        return null;
    }
}
