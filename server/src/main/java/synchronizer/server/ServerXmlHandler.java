package synchronizer.server;

import synchronizer.common.LoggerWrapper;
import synchronizer.common.util.FileUtil;
import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.XmlUserParser;
import synchronizer.common.xml.schema.XMLUsers;
import synchronizer.server.dao.UserDao;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * GKislin
 * 05.03.2015.
 */
public class ServerXmlHandler {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ServerXmlHandler.class);

    private final XmlParser parser = XmlUserParser.get();

    public void processChunkFile(final Path chunkFile) {
        if (!chunkFile.toString().endsWith(".xml")) {
            return;
        }
        LOG.info("Receive " + chunkFile);
        checkNotNull(chunkFile);
        final XMLUsers xmlUsers;
        try {
            try (Reader reader = Files.newBufferedReader(chunkFile, StandardCharsets.UTF_8)) {
                xmlUsers = parser.unmarshall(reader);
            }
            // TODO rename flag file chunkFile.db_saving
            UserDao.save(xmlUsers.getXMLUser());
            // TODO rename flag file to chunkFile.done
            // delete only after saving into DB.
            Files.delete(chunkFile);
            LOG.info(chunkFile + " have been processed");

        } catch (IOException | JAXBException | SQLException e) {
            LOG.error("Exception during processing received file " + chunkFile, e);
            try {
                FileUtil.replaceExtension(chunkFile, "bad");
            } catch (IOException e1) {
                throw LOG.getIllegalStateException("Cannot rename " + chunkFile + " file");
            }
        }
    }
}
