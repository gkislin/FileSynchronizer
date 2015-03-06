package synchronizer.common.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import synchronizer.common.LoggerWrapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: gkislin
 * Date: 12.09.13
 */
abstract public class AbstractConfig {
    private final LoggerWrapper log = LoggerWrapper.get(getClass());

    protected final Config config;
    protected final String configName;

    public AbstractConfig(String configName) {
        this.configName = configName;
        this.config = ConfigFactory.parseResources(configName);
    }

    protected Path getDirectory(String key) {
        Path path = Paths.get(config.getString(key));
        if (!Files.isDirectory(path)) {
            throw log.getIllegalStateException(key + " in config " + configName + " is not directory");
        }
        return path;
    }
}
