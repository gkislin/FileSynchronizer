package synchronizer.common.util;

import synchronizer.common.xml.XmlParser;
import synchronizer.common.xml.schema.AddressType;
import synchronizer.common.xml.schema.FlagType;
import synchronizer.common.xml.schema.XMLUser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

/**
 * GKislin
 * 16.03.2015.
 * <p>
 * TODO remove to common test
 */
public class XmlUserUtil {
    private static final Random RANDOM = new Random();

    public static int randomInRange(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }

    public static XMLUser generate() throws IOException, JAXBException {
        XMLUser user = new XMLUser();
        int rnd = RANDOM.nextInt(1000);
        String code = UUID.randomUUID().toString();
        user.setCode(code);
        user.setUsername("Username_" + rnd);
        user.setFirstname("Firstname_" + rnd);
        user.setLastname("Lastname_" + rnd);
        GregorianCalendar gregorianCalendar = new GregorianCalendar(randomInRange(1915, 2015), randomInRange(0, 11), randomInRange(1, 31));
        user.setBirth(XmlParser.toXmlCalendar(gregorianCalendar));
        AddressType address = new AddressType();
        address.setCity("City_" + rnd);
        address.setCountry("Country_" + rnd);
        address.setStreet("Street_" + rnd);
        user.setAddress(address);
        user.setAge(rnd/10);
        user.setFlag(FlagType.ACTIVE);
        return user;
    }

    public static boolean compare(XMLUser user1, XMLUser user2) {
        return user1.getCode().equals(user2.getCode()) &&
                user1.getUsername().equals(user2.getUsername()) &&
                user1.getFirstname().equals(user2.getFirstname()) &&
                user1.getLastname().equals(user2.getLastname()) &&
                user1.getBirth().equals(user2.getBirth()) &&
                user1.getAddress().getCountry().equals(user2.getAddress().getCountry()) &&
                user1.getAddress().getCity().equals(user2.getAddress().getCity()) &&
                user1.getAddress().getStreet().equals(user2.getAddress().getStreet()) &&
                user1.getAge().equals(user2.getAge()) &&
                user1.getFlag().equals(user2.getFlag());
    }
}
