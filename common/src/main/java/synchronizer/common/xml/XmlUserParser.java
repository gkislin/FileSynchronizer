package synchronizer.common.xml;

import com.google.common.io.Resources;
import synchronizer.common.xml.schema.ObjectFactory;

/**
 * GKislin
 * 05.03.2015.
 */
public class XmlUserParser {
    private static final XmlParser PARSER = new XmlParser(Resources.getResource("userSchema.xsd"), ObjectFactory.class);

    public static XmlParser get() {
        return PARSER;
    }
}
