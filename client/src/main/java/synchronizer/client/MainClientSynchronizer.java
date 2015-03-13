package synchronizer.client;

import com.google.common.collect.Lists;
import synchronizer.common.LoggerWrapper;
import synchronizer.common.Statistic;
import synchronizer.common.util.DirectoryScanner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
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
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);

        // TODO scan chunkFile.deleting for recover deleting if failed

        // Scan xml files and process them
        executor.scheduleWithFixedDelay(() -> {
            try (DirectoryStream<Path> directoryStream = DirectoryScanner.getDirectoryStream(config.getSyncDirectory())) {
                // Log statistic here as all operation is asynchronous
                LOG.info("Statistic:\n" + Statistic.get().toString());
                Statistic.get().clear();

                final List<Path> list = Lists.newArrayList(directoryStream.iterator());
                // Sort for delete recovering
                list.sort((p1, p2) -> p1.getFileName().compareTo(p2.getFileName()));

                Lists.partition(list, config.getChunk()).forEach(chunk -> {
                    executor.submit(() -> {
                        xmlHandler.processChunkFiles(chunk);
                        return null;
                    });
                });
            } catch (Exception e) {
                throw LOG.getIllegalStateException("Error " + config.getSyncDirectory() + " processing", e);
            }
        }, 0, config.getSyncTime(), TimeUnit.SECONDS);
    }
}
