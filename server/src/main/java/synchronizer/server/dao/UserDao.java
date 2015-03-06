package synchronizer.server.dao;

import synchronizer.common.xml.schema.AddressType;
import synchronizer.common.xml.schema.XMLUser;
import synchronizer.server.db.Sql;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 05.03.2015.
 */
public class UserDao {

    public void save(List<XMLUser> users) throws SQLException {
        Sql.get().execute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users (code, username, firstname, lastname, age, birth, country, city, street, flag)" +
                            " VALUES (?,?,?,?,?,?,?,?,?,?)")) {
                for (XMLUser user : users) {
                    ps.setString(1, user.getCode());
                    ps.setString(2, user.getUsername());
                    ps.setString(3, user.getFirstname());
                    ps.setString(4, user.getLastname());
                    ps.setInt(5, user.getAge());
                    XMLGregorianCalendar birth = user.getBirth();
                    ps.setDate(6, Date.valueOf(LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDay())));
                    AddressType address = user.getAddress();
                    ps.setString(7, address.getCountry());
                    ps.setString(8, address.getCity());
                    ps.setString(9, address.getStreet());
                    ps.setInt(10, user.getFlag().ordinal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        });
    }
}
