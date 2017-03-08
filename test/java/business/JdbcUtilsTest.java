package business;

import java.sql.Connection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 */
public class JdbcUtilsTest {
    @BeforeEach
    public void setUp() throws Exception {
        IntegrationTestPlatform.setupJNDIContext();
    }

    @AfterEach
    public void tearDown() throws Exception {
        IntegrationTestPlatform.teardownJNDIContext();
    }

    @Test
    public void canGetAConnectionFromTheTestJNDIContext() throws Exception {
        Connection c = JdbcUtils.getConnection();
        assertNotNull(c);
    }

}
