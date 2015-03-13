package synchronizer.client;

import synchronizer.common.LoggerWrapper;
import synchronizer.common.Statistic;
import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.XmlParserFactory;
import synchronizer.common.xml.schema.XMLUser;
import synchronizer.common.xml.schema.XMLUsers;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * GKislin
 * 05.03.2015.
 */
public class ClientXmlHandler {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ClientXmlHandler.class);

    private final XmlParser parser = XmlParserFactory.getParser();
    final FileSender fileSender = new FileSender();

    public void processChunkFiles(final List<Path> paths) throws IOException, JAXBException {
        checkArgument(!paths.isEmpty());

        final XMLUsers users = createXMLUsers(paths);
        final List<XMLUser> xmlUser = users.getXMLUser();
        final String chunkFile = "chunk_" + xmlUser.get(0).getCode() + '-' + xmlUser.get(xmlUser.size() - 1).getCode() + ".xml";
        LOG.info("Send chunk " + chunkFile);

        final long start = System.currentTimeMillis();

        // TODO set flag file: chunkFile.writing (server side when network transfer)
        try (Writer writer = fileSender.getWriter(chunkFile)) {
            if (writer != null) {
                parser.marshall(users, writer);
            }
        }
        Statistic.get().addRecord(chunkFile, (int) (System.currentTimeMillis() - start));

        // TODO rename file flag to chunkFile.deleting
        for (Path p : paths) {
            Files.delete(p);
        }
        // TODO remove chunkFile.deleting
    }

    public XMLUsers createXMLUsers(final List<Path> list) throws IOException, JAXBException {
        final XMLUsers xmlUsers = new XMLUsers();
        final List<XMLUser> userList = xmlUsers.getXMLUser();
        for (Path path : list) {
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                userList.add(parser.unmarshall(reader));
            }
        }
        return xmlUsers;
    }
}
