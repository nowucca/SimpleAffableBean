package business;

import java.sql.Connection;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 */
public class JdbcUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getConnection() throws Exception {
        Connection c = JdbcUtils.getConnection();
        assertNotNull(c);

    }

}
