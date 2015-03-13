package synchronizer.server;

import synchronizer.common.LoggerWrapper;
import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.XmlParserFactory;
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

    private final XmlParser parser = XmlParserFactory.getParser();
    private final UserDao userDao = new UserDao();

    public void processChunkFile(final Path chunkFile) {
        LOG.info("Receive " + chunkFile);
        checkNotNull(chunkFile);
        final XMLUsers xmlUsers;
        try {
            try (Reader reader = Files.newBufferedReader(chunkFile, StandardCharsets.UTF_8)) {
                xmlUsers = parser.unmarshall(reader);
            }
            // TODO rename flag file chunkFile.db_saving
            userDao.save(xmlUsers.getXMLUser());
            // TODO rename flag file to chunkFile.done
            // delete only after saving into DB.
            Files.delete(chunkFile);
            LOG.info(chunkFile + " have been processed");

        } catch (IOException | JAXBException | SQLException e) {
            throw LOG.getIllegalStateException("Exception during processing received file " + chunkFile, e);
        }
    }
}
