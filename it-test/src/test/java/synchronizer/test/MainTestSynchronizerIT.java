package synchronizer.test;

import org.junit.Test;
import synchronizer.client.ClientConfig;
import synchronizer.common.LoggerWrapper;
import synchronizer.common.util.FileUtil;
import synchronizer.common.util.XmlUserUtil;
import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.XmlUserParser;
import synchronizer.common.xml.schema.XMLUser;
import synchronizer.server.dao.UserDao;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * GKislin
 * 14.03.2015.
 */
public class MainTestSynchronizerIT {
    private static final LoggerWrapper LOG = LoggerWrapper.get(MainTestSynchronizerIT.class);
    private static final int THREAD_NUMBER = 10;
    volatile static boolean error = false;

    @Test
    public void runTest() throws IOException, InterruptedException, SQLException {
        final ExecutorService executor = Executors.newCachedThreadPool();

        UserDao.clear();
        CountDownLatch latch = new CountDownLatch(THREAD_NUMBER);

        for (int j = 0; j < THREAD_NUMBER; j++) {
            Thread.sleep(100);
            executor.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        final XMLUser user = generateXml();
                        final String code = user.getCode();
                        System.out.println("Generate " + code);
                        Thread.sleep(ClientConfig.get().getSyncTime()*1500);
                        XMLUser dbUser = UserDao.get(code);
                        if (dbUser == null) {
                            LOG.error(user + " not found in DB");
                            error = true;
                        } else {
                            if (!XmlUserUtil.compare(user, dbUser)) {
                                LOG.error(user + " not equals " + dbUser);
                                error = true;
                            }
                        }
                        return null;
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        latch.await();
        if (error) throw LOG.getIllegalStateException("Error found during test");
    }

    private static final XmlParser PARSER = XmlUserParser.get();

    private static XMLUser generateXml() throws IOException, JAXBException {
        XMLUser user = XmlUserUtil.generate();
        Path generatedFile = ClientConfig.get().getSyncDirectory().resolve(user.getCode());
        try (Writer writer = Files.newBufferedWriter(generatedFile)) {
            PARSER.marshall(user, writer);
        }
        FileUtil.addExtension(generatedFile, "xml");
        return user;
    }

}
