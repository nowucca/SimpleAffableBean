package business;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 */
public class IntegrationTestPlatform {

    @BeforeAll
    public static void setupJNDIContext() throws Exception {
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:comp");
            Context compEnvCtx = ic.createSubcontext("java:comp/env");
            ic.createSubcontext("java:comp/env/jdbc");

            MysqlConnectionPoolDataSource mysqlDS = new MysqlConnectionPoolDataSource();
            mysqlDS.setURL("jdbc:mysql://localhost:3306/simpleaffablebean");
            mysqlDS.setUser("simpleaffablebean");
            mysqlDS.setPassword("simpleaffablebean");

            compEnvCtx.bind(JdbcUtils.JDBC_SIMPLEAFFABLEBEAN, mysqlDS);
        } catch (NamingException e) {
            throw new RuntimeException("Failed to establish a JNDI context", e);
        }
    }

    @AfterAll
    public static void teardownJNDIContext() throws Exception {
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
            InitialContext ic = new InitialContext();
            Context envCtx = (Context) ic.lookup("java:comp/env");
            envCtx.unbind(JdbcUtils.JDBC_SIMPLEAFFABLEBEAN);
            ic.destroySubcontext("java:comp");
        } catch (NamingException e) {
            throw new RuntimeException("Failed to teardown a JNDI context", e);
        }
    }

    @BeforeEach
    public void createFreshPopulatedDatabase() throws Exception {
        ScriptRunner runner = new ScriptRunner(JdbcUtils.getConnection(), false, false);
        String file = "setup/schemaCreation.sql";
        runner.runScript(new BufferedReader(new FileReader(file)));
        file = "setup/sampleData.sql";
        runner.runScript(new BufferedReader(new FileReader(file)));
    }
}
