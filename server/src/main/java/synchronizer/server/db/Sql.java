package synchronizer.server.db;

import synchronizer.common.LoggerWrapper;
import synchronizer.server.ServerConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * GKislin
 * 13.02.2015.
 */
public class Sql {
    private static final Sql INSTANCE = new Sql(ServerConfig.get().getDbUrl());
    private static final LoggerWrapper LOG = LoggerWrapper.get(Sql.class);

    private final ConnectionFactory connectionFactory;

    public Sql(String dbUrl) {
        //TODO configure pool in web container and get connection from it by JNDI
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl);
    }

    public static Sql get() {
        return INSTANCE;
    }

    public void execute(SqlTransaction executor) throws SQLException {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                executor.execute(conn);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                LOG.warn("Transaction rollback");
            }
        }
    }

    public interface ConnectionFactory {
        Connection getConnection() throws SQLException;
    }

    public interface SqlTransaction {
        void execute(Connection conn) throws SQLException;
    }
}
