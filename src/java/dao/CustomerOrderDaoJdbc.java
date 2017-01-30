package dao;

import entity.CustomerOrder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static dao.JdbcUtils.getConnection;

/**
 */
public class CustomerOrderDaoJdbc implements CustomerOrderDao {

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

    private CustomerOrderLineItemDao lineItemDao;


    @Override
    public CustomerOrder findByCustomerId(long customerId) {
        CustomerOrder result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ID_SQL)) {
            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomerOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem finding customer "+customerId, e);
        }
        if (result != null) {
            result.setCustomerOrderLineItems(lineItemDao.findByCustomerOrderId(result.getCustomerOrderId()));
        }
        return result;
    }

    @Override
    public List<CustomerOrder> findAll() {
        List<CustomerOrder> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            CustomerOrder customerOrder = readCustomerOrder(resultSet);
            result.add(customerOrder);
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem finding all categories", e);
        }

        result.forEach((order) ->
            order.setCustomerOrderLineItems(lineItemDao.findByCustomerOrderId(order.getCustomerOrderId())));

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

    public void setLineItemDao(CustomerOrderLineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }
}
