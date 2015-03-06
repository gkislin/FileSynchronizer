package synchronizer.common.xml;

import org.xml.sax.SAXException;
import synchronizer.common.LoggerWrapper;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

/**
 * User: gkislin
 *
 * Thread-safe (by ThreadLocal) JAXB realization with validation
 * @link https://jaxb.java.net/guide/Performance_and_thread_safety.html
 */
public class XmlParser {
    private static final LoggerWrapper LOG = LoggerWrapper.get(XmlParser.class);

    // Validation
    private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    // Unsafe, keep in ThreadLocal
    private final ThreadLocal<Marshaller> marshallerThreadLocal = new ThreadLocal<Marshaller>() {
        @Override
        protected Marshaller initialValue() {
            Marshaller marshaller;
            try {
                marshaller = ctx.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                marshaller.setSchema(schema);
            } catch (JAXBException e) {
                throw LOG.getIllegalStateException("Marshaller creating error", e);
            }
            return marshaller;
        }
    };

    private final ThreadLocal<Unmarshaller> unmarshallerThreadLocal = new ThreadLocal<Unmarshaller>() {
        @Override
        protected Unmarshaller initialValue() {
            Unmarshaller unmarshaller;
            try {
                unmarshaller = ctx.createUnmarshaller();
                unmarshaller.setSchema(schema);
            } catch (JAXBException e) {
                throw LOG.getIllegalStateException("Marshaller creating error", e);
            }
            return unmarshaller;
        }
    };

    private final JAXBContext ctx;
    private final Schema schema;

    public XmlParser(URL schemaURL, Class... classesToBeBound) {
        try {
            this.schema = SCHEMA_FACTORY.newSchema(schemaURL);
            ctx = JAXBContext.newInstance(classesToBeBound);
        } catch (JAXBException | SAXException e) {
            throw LOG.getIllegalStateException("Jaxb init failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T unmarshall(Reader reader) throws JAXBException {
        return (T) unmarshallerThreadLocal.get().unmarshal(reader);
    }

    public void marshall(Object instance, Writer writer) throws JAXBException {
        marshallerThreadLocal.get().marshal(instance, writer);
    }
}
