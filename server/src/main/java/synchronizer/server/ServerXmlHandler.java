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

    private XmlParser parser = XmlParserFactory.getParser();
    private UserDao userDao = new UserDao();

    public void processChunkFile(final Path chunkFile) {
        LOG.info("Receive " + chunkFile);
        checkNotNull(chunkFile);
        XMLUsers xmlUsers;
        try {
            // Wait until file received
            // http://stackoverflow.com/questions/3369383/java-watching-a-directory-to-move-large-files/3369416#3369416
            // TODO wait based on file flag ?
            Thread.sleep(500);
            try (Reader reader = Files.newBufferedReader(chunkFile, StandardCharsets.UTF_8)) {
                xmlUsers = parser.unmarshall(reader);
            }
            userDao.save(xmlUsers.getXMLUser());

            // delete only after saving into DB.
            Files.delete(chunkFile);

        } catch (IOException | JAXBException | SQLException | InterruptedException e) {
            throw LOG.getIllegalStateException("Exception during processing received file " + chunkFile, e);
        }
    }
}
