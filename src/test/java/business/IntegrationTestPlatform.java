/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package business;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
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

            ic.createSubcontext("java:comp");
            Context compEnvCtx = ic.createSubcontext("java:comp/env");
            ic.createSubcontext("java:comp/env/jdbc");

            MysqlConnectionPoolDataSource mysqlDS = new MysqlConnectionPoolDataSource();
            mysqlDS.setURL("jdbc:mysql://localhost:3306/simpleaffablebean");
            mysqlDS.setUser("simpleaffablebean");
            mysqlDS.setPassword("simpleaffablebean");
            mysqlDS.setAllowPublicKeyRetrieval(true);
            mysqlDS.setServerTimezone("PST");

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
        ScriptRunner runner = new ScriptRunner(JdbcUtils.getConnection(), false, true);
        String file = "src/main/db/schemaCreation.sql";
        runner.runScript(new BufferedReader(new FileReader(file)));
        file = "src/main/db/sampleData.sql";
        runner.runScript(new BufferedReader(new FileReader(file)));
    }
}
