/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.info>
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
package business.customer;

import business.SimpleAffableDbException.SimpleAffableQueryDbException;
import business.SimpleAffableDbException.SimpleAffableUpdateDbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CustomerDaoJdbc implements CustomerDao {

    private static final String CREATE_CUSTOMER_SQL =
        "INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_ALL_SQL =
        "SELECT " +
            "c.customer_id, c.name, c.email, " +
            "c.phone, c.address, c.city_region, c.cc_number " +
        "FROM " +
            "customer c";

    private static final String FIND_BY_CUSTOMER_ID_SQL =
        "SELECT " +
            "c.customer_id, c.name, c.email, " +
            "c.phone, c.address, c.city_region, c.cc_number " +
        "FROM " +
            "customer c " +
        "WHERE " +
            "c.customer_id = ?";


    @Override
    public long create(final Connection connection, CustomerForm customerForm) {
        try (PreparedStatement statement =
                 connection.prepareStatement(CREATE_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customerForm.getName());
            statement.setString(2, customerForm.getEmail());
            statement.setString(3, customerForm.getPhone());
            statement.setString(4, customerForm.getAddress());
            statement.setString(5, customerForm.getCityRegion());
            statement.setString(6, customerForm.getCcNumber());
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new SimpleAffableUpdateDbException("Failed to insert a customer, affected row count = "
                    + affected);
            }
            long customerId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerId = rs.getLong(1);
            } else {
                throw new SimpleAffableQueryDbException("Failed to retrieve customerId auto-generated key");
            }

            return customerId;
        } catch (SQLException e) {
            throw new SimpleAffableUpdateDbException("Encountered problem creating a new customer ", e);
        }
    }

    @Override
    public Customer findByCustomerId(long customerId) {
        Customer result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ID_SQL)) {
            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding customer " + customerId, e);
        }
        return result;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Customer c = readCustomer(resultSet);
                result.add(c);
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding all categories", e);
        }

        return result;
    }

    private Customer readCustomer(ResultSet resultSet) throws SQLException {
       Long customerId = resultSet.getLong("customer_id");
       String name = resultSet.getString("name");
       String email = resultSet.getString("email");
       String phone = resultSet.getString("phone");
       String address = resultSet.getString("address");
       String cityRegion = resultSet.getString("city_region");
       String cc_number = resultSet.getString("cc_number");
       return new Customer(customerId, name, email, phone, address, cityRegion, cc_number);
    }
}
