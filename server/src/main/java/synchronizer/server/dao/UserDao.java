package synchronizer.server.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.tomcat.jdbc.pool.DataSource;
import synchronizer.common.xml.schema.AddressType;
import synchronizer.common.xml.schema.XMLUser;
import synchronizer.server.ServerConfig;

import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * GKislin
 * 05.03.2015.
 */
public class UserDao {

    private static final QueryRunner RUNNER;
    private static final ResultSetHandler<List<DbUser>> USER_LIST_HANDLER = new BeanListHandler<>(DbUser.class);
    private static final ResultSetHandler<DbUser> USER_HANDLER = new BeanHandler<>(DbUser.class);

    static {
        final DataSource ds = new DataSource();
        ds.setUrl(ServerConfig.get().getDbUrl());
        ds.setDriverClassName("org.postgresql.Driver");
        RUNNER = new QueryRunner(ds);
    }

    public static void save(List<XMLUser> users) throws SQLException {
        final List<Object[]> params = new ArrayList<>(users.size());
        users.forEach(user -> {
                    XMLGregorianCalendar birth = user.getBirth();
                    AddressType address = user.getAddress();
                    params.add(
                            new Object[]{user.getCode(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getAge(),
                                    Date.valueOf(LocalDate.of(birth.getYear(), birth.getMonth(), birth.getDay())),
                                    address.getCountry(), address.getCity(), address.getStreet(), user.getFlag().name()}
                    );
                }
        );
        RUNNER.batch("INSERT INTO users (code, username, firstname, lastname, age, birth, country, city, street, flag)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)", params.toArray(new Object[users.size()][10]));
    }

    public static void clear() throws SQLException {
        RUNNER.update("DELETE FROM users");
    }

    public static XMLUser get(String code) throws SQLException {
        return RUNNER.query("SELECT code, username, firstname, lastname, age, birth as birthDate, country, city, street, flag FROM users WHERE code=?", USER_HANDLER, code);
    }

    public static List<DbUser> getAll() throws SQLException {
        return RUNNER.query("SELECT * FROM users", USER_LIST_HANDLER);
    }
}
