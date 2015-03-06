package synchronizer.client;

import com.google.common.collect.Iterators;
import synchronizer.common.LoggerWrapper;
import synchronizer.common.Statistic;
import synchronizer.common.util.DirectoryScanner;
import synchronizer.common.util.ScheduledExecutor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * GKislin
 * 03.03.2015.
 */
public class MainClientSynchronizer {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MainClientSynchronizer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        final ClientConfig config = ClientConfig.get();
        final ClientXmlHandler xmlHandler = new ClientXmlHandler();
        final ScheduledExecutor executor = new ScheduledExecutor();

        // Scan xml files and process them
        executor.scheduleWithFixedDelay(() -> {
            try (DirectoryStream<Path> directoryStream = DirectoryScanner.getDirectoryStream(config.getSyncDirectory())) {
                // Log statistic here as all operation is asynchronous
                LOG.info("Statistic:\n" + Statistic.get().toString());
                Statistic.get().clear();

                Iterator<List<Path>> fileChunks = Iterators.partition(directoryStream.iterator(), config.getChunk());

                while (fileChunks.hasNext()) {
                    final List<Path> chunk = fileChunks.next();
                    executor.submit(() -> {
                        xmlHandler.processChunkFiles(chunk);
                        return null;
                    });
                }
            } catch (Exception e) {
                throw LOG.getIllegalStateException("Error " + config.getSyncDirectory() + " processing", e);
            }
        }, 0, config.getSyncTime(), TimeUnit.SECONDS);
    }
}
