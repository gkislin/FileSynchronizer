
package synchronizer.common.xml.schema;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element ref="{https://cargoonline.ru}XMLUser"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xmlUser"
})
@XmlRootElement(name = "XMLUsers", namespace = "https://cargoonline.ru")
public class XMLUsers {

    @XmlElement(name = "XMLUser", namespace = "https://cargoonline.ru", required = true)
    protected List<XMLUser> xmlUser;

    /**
     * Gets the value of the xmlUser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xmlUser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXMLUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLUser }
     * 
     * 
     */
    public List<XMLUser> getXMLUser() {
        if (xmlUser == null) {
            xmlUser = new ArrayList<XMLUser>();
        }
        return this.xmlUser;
    }

}
