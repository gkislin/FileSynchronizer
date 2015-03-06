package synchronizer.client;

import synchronizer.common.config.AbstractConfig;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * GKislin
 * 03.03.2015.
 */
public class ClientConfig extends AbstractConfig {

    private static final ClientConfig INSTANCE = new ClientConfig();
    private final String serverAddress;
    private final int serverPort;
    private final int chunk;
    private final Path syncDirectory;
    private final long syncTime;

    // temporary, replaced by network
    private final Path targetDirectory;

    private ClientConfig() {
        super("client.properties");
        serverAddress = config.getString("server.address");
        serverPort = config.getInt("server.port");
        chunk = config.getInt("chunk");
        syncDirectory = getDirectory("sync.directory");
        targetDirectory = getDirectory("target.directory");
        syncTime = config.getDuration("sync.time", TimeUnit.SECONDS);
    }

    public static ClientConfig get() {
        return INSTANCE;
    }

    public int getChunk() {
        return chunk;
    }

    public Path getSyncDirectory() {
        return syncDirectory;
    }

    public Path getTargetDirectory() {
        return targetDirectory;
    }

    public long getSyncTime() {
        return syncTime;
    }
}
