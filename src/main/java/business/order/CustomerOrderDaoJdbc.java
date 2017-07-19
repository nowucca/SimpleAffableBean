/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
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
package business.order;

import business.SimpleAffableDbException.SimpleAffableQueryDbException;
import business.SimpleAffableDbException.SimpleAffableUpdateDbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CustomerOrderDaoJdbc implements CustomerOrderDao {

    private static final String CREATE_ORDER_SQL =
        "INSERT INTO customer_order (amount, customer_id, confirmation_number) " +
            "VALUES (?, ?, ?)";

    private static final String FIND_ALL_SQL =
        "SELECT " +
            "co.customer_order_id, co.customer_id, co.amount, co.date_created, co.confirmation_number " +
        "FROM " +
            "customer_order co";

    private static final String FIND_BY_CUSTOMER_ID_SQL =
        "SELECT " +
            "co.customer_order_id, co.customer_id, co.amount, co.date_created, co.confirmation_number " +
        "FROM " +
            "customer_order co " +
        "WHERE " +
            "co.customer_id = ?";

    private static final String FIND_BY_CUSTOMER_ORDER_ID_SQL =
        "SELECT " +
            "co.customer_order_id, co.customer_id, co.amount, co.date_created, co.confirmation_number " +
            "FROM " +
            "customer_order co " +
            "WHERE " +
            "co.customer_order_id = ?";

    @Override
    public long create(final Connection connection, long customerId, int amount, int confirmationNumber) {
        try (PreparedStatement statement =
                 connection.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, amount);
            statement.setLong(2, customerId);
            statement.setInt(3, confirmationNumber);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new SimpleAffableUpdateDbException("Failed to insert an order, affected row count = "
                    + affected);
            }
            long customerOrderId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerOrderId = rs.getLong(1);
            } else {
                throw new SimpleAffableQueryDbException("Failed to retrieve customerOrderId auto-generated key");
            }

            return customerOrderId;
        } catch (SQLException e) {
            throw new SimpleAffableUpdateDbException("Encountered problem creating a new customer ", e);
        }
    }

    @Override
    public CustomerOrder findByCustomerId(long customerId) {
        return getCustomerOrderBy(FIND_BY_CUSTOMER_ID_SQL, customerId);
    }

    @Override
    public CustomerOrder findByCustomerOrderId(long customerOrderId) {
        return getCustomerOrderBy(FIND_BY_CUSTOMER_ORDER_ID_SQL, customerOrderId);
    }

    @Override
    public Collection<CustomerOrder> findAll() {
        List<CustomerOrder> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CustomerOrder customerOrder = readCustomerOrder(resultSet);
                result.add(customerOrder);
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding all orders", e);
        }

        return result;
    }

    private CustomerOrder getCustomerOrderBy(String sql, long id) {
        CustomerOrder result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomerOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding customer order id=" + id, e);
        }
        return result;
    }

    private CustomerOrder readCustomerOrder(ResultSet resultSet) throws SQLException {
        Long customerOrderId = resultSet.getLong("customer_order_id");
       Long customerId = resultSet.getLong("customer_id");
       int amount = resultSet.getInt("amount");
       Date dateCreated = resultSet.getTimestamp("date_created");
       int confirmationNumber = resultSet.getInt("confirmation_number");
       return new CustomerOrder(customerOrderId, customerId, amount, dateCreated, confirmationNumber);
    }

}
