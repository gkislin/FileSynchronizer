package synchronizer.server;

import synchronizer.common.LoggerWrapper;
import synchronizer.common.util.DirectoryScanner;
import synchronizer.common.util.ScheduledExecutor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * GKislin
 * 03.03.2015.
 */
public class MainServerSynchronizer {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MainServerSynchronizer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        final ServerXmlHandler xmlHandler = new ServerXmlHandler();
        final ScheduledExecutor executor = new ScheduledExecutor();
        final DirectoryScanner scanner = new DirectoryScanner(ServerConfig.get().getReceivingDirectory());

        //TODO make scanning and deleting todo chunkFile.done flag files older that (now - <maximum outage period>)

        // scan for receiving files
        executor.scheduleWithFixedDelay(() -> {
            try {
                scanner.process(xmlHandler::processChunkFile);
            } catch (IOException | InterruptedException e) {
                throw LOG.getIllegalStateException("Error " + ServerConfig.get().getReceivingDirectory() + " processing", e);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
