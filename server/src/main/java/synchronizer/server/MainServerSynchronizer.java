package synchronizer.server;

import synchronizer.common.LoggerWrapper;
import synchronizer.common.util.DirectoryScanner;
import synchronizer.common.util.ScheduledExecutor;

import java.io.IOException;

/**
 * GKislin
 * 03.03.2015.
 */
public class MainServerSynchronizer {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MainServerSynchronizer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerXmlHandler xmlHandler = new ServerXmlHandler();
        final ScheduledExecutor executor = new ScheduledExecutor();

        DirectoryScanner scanner = new DirectoryScanner(ServerConfig.get().getReceivingDirectory());

        // process remaining receiving files
        scanner.process(xmlHandler::processChunkFile);

        // scan for new receiving files
        try {
            scanner.scanAndProcess(xmlHandler::processChunkFile);
        } catch (IOException | InterruptedException e) {
            throw LOG.getIllegalStateException("Error " + ServerConfig.get().getReceivingDirectory() + " processing", e);
        }
    }
}
