package business;

import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.jupiter.api.Test;

/**
 */
public class BusinessTest extends IntegrationTestPlatform {


	@Test
	public void testBusinessTestSetupAndTeardown() throws Exception {
		ScriptRunner runner = new ScriptRunner(JdbcUtils.getConnection(), false, true);
		String file = "src/main/db/testSampleData.sql";
		runner.runScript(new BufferedReader(new FileReader(file)));
	}
}
