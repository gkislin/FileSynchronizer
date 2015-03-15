package synchronizer.server.dao;

import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.schema.AddressType;
import synchronizer.common.xml.schema.XMLUser;

import java.util.Date;

/**
 * GKislin
 * 16.03.2015.
 */
public class DbUser extends XMLUser {
    public void setStreet(String street) {
        if (address == null) {
            address = new AddressType();
        }
        address.setStreet(street);
    }

    public void setCity(String city) {
        if (address == null) {
            address = new AddressType();
        }
        address.setCity(city);
    }

    public void setCountry(String country) {
        if (address == null) {
            address = new AddressType();
        }
        address.setCountry(country);
    }

    public void setBirthDate(Date birthDate) {
        this.birth = XmlParser.toXmlCalendar(birthDate);
    }
}
