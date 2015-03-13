package synchronizer.client;

import synchronizer.common.util.FileUtil;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * GKislin
 * 05.03.2015.
 * <p>
 * TODO implement network sending
 * Mock for test: just copy to other dir with overriding
 */
public class FileTransfer {
    private final Path target;

    public FileTransfer(String source) {
        target = ClientConfig.get().getTargetDirectory().resolve(source);
    }

    public Writer getWriter() throws IOException {
        return Files.newBufferedWriter(target, StandardCharsets.UTF_8);
    }

    public boolean isTransfered() {
        return Files.exists(target);
    }

    // rename to name.xml
    public void finish() throws IOException {
        FileUtil.addExtension(target, "xml");
    }
}
