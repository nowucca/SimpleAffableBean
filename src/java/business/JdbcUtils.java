package business;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 */
public class JdbcUtils {

    private static final String JDBC_SIMPLEAFFABLEBEAN = "jdbc/simpleaffablebean";

    private static DataSource dataSource;

    public static Connection getConnection()  {
        if (dataSource == null) {
            dataSource = getDataSource(JDBC_SIMPLEAFFABLEBEAN);
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Encountered a SQL issue getting a connection", e);
        }

    }

    private static DataSource getDataSource(String dataSourceName)  {
        try {
            InitialContext initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            return (DataSource) envCtx.lookup(dataSourceName);
        } catch (NamingException e) {
            throw new IllegalArgumentException("Encountered an issue establishing an initial JNDI context", e);
        }
    }



}
