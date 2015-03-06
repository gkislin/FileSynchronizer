package synchronizer.common.xml;

import com.google.common.io.Resources;
import synchronizer.common.xml.schema.ObjectFactory;

/**
 * GKislin
 * 05.03.2015.
 */
public class XmlParserFactory {
    public static XmlParser getParser() {
        return new XmlParser(Resources.getResource("userSchema.xsd"), ObjectFactory.class);
    }
}
