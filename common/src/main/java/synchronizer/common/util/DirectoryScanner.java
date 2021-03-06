package synchronizer.common.util;

import com.google.common.collect.Lists;
import synchronizer.common.LoggerWrapper;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

/**
 * GKislin
 * 05.03.2015.
 */
public class DirectoryScanner {
    private static final LoggerWrapper LOG = LoggerWrapper.get(DirectoryScanner.class);

    private final WatchService watcher;
    private final Path scannedDir;

    public DirectoryScanner(Path dir) {
        checkArgument(Files.isDirectory(dir));
        this.scannedDir = dir;
        try {
            watcher = FileSystems.getDefault().newWatchService();
            scannedDir.register(watcher, ENTRY_CREATE);
        } catch (IOException e) {
            throw LOG.getIllegalStateException("Error creating DirectoryScanner of " + dir, e);
        }
    }

    public static DirectoryStream<Path> getDirectoryStream(Path dir) throws IOException {
        return Files.newDirectoryStream(dir,
                path -> Files.isRegularFile(path) && path.toString().endsWith(".xml"));
    }

    public void process(Consumer<Path> consumer) throws IOException, InterruptedException {
        List<Path> received = Lists.newArrayList(getDirectoryStream(scannedDir));
        received.forEach(consumer::accept);
    }

    public void scanAndProcess(Consumer<Path> consumer) throws IOException, InterruptedException {
        while (true) {
            WatchKey key = watcher.take();
            key.pollEvents().forEach(event -> consumer.accept(scannedDir.resolve(((WatchEvent<Path>) event).context())));
            if (!key.reset()) {
                throw LOG.getIllegalStateException("Directory " + scannedDir + " is inaccessible");
            }
        }
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        DirectoryScanner directoryScanner = new DirectoryScanner(Paths.get("d:\\Temp\\"));
//        directoryScanner.scanAndProcess(System.out::println);
//    }
}
