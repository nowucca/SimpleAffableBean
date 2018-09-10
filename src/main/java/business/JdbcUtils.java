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

import business.SimpleAffableDbException.SimpleAffableConnectionDbException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 */
public final class JdbcUtils {

    private JdbcUtils() {
    }

    static final String JDBC_SIMPLEAFFABLEBEAN = "jdbc/simpleaffablebean";

    private static DataSource dataSource;

    public static Connection getConnection()  {
        if (dataSource == null) {
            dataSource = getDataSource(JDBC_SIMPLEAFFABLEBEAN);
        }

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new SimpleAffableConnectionDbException("Encountered a SQL issue getting a connection", e);
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
