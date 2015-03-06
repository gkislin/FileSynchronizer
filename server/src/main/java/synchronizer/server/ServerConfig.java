package synchronizer.server;

import synchronizer.common.config.AbstractConfig;

import java.nio.file.Path;

/**
 * GKislin
 * 03.03.2015.
 */
public class ServerConfig extends AbstractConfig {

    private static final ServerConfig INSTANCE = new ServerConfig();

    private final String dbUrl;
    private final int serverPort;
    private final Path receivingDirectory;

    private ServerConfig() {
        super("server.properties");
        dbUrl = config.getString("db.url");
        serverPort = config.getInt("server.port");
        receivingDirectory = getDirectory("receiving.directory");
    }

    public static ServerConfig get() {
        return INSTANCE;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public int getServerPort() {
        return serverPort;
    }

    public Path getReceivingDirectory() {
        return receivingDirectory;
    }
}
