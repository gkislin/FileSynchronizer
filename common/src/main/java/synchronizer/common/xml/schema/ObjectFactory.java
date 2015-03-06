
package synchronizer.common.xml.schema;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the synchronizer.client.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: synchronizer.client.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XMLUser }
     * 
     */
    public XMLUser createXMLUser() {
        return new XMLUser();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link XMLUsers }
     * 
     */
    public XMLUsers createXMLUsers() {
        return new XMLUsers();
    }

}
