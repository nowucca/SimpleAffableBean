package business.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    private CustomerOrderLineItemDao lineItemDao;


    @Override
    public long create(final Connection connection, long customerId, int amount, int confirmationNumber) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, amount);
            statement.setLong(2, customerId);
            statement.setInt(3, confirmationNumber);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new RuntimeException("Failed to insert an order, affected row count = "+affected);
            }
            long customerOrderId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerOrderId = rs.getLong(1);
            } else {
                throw new RuntimeException("Failed to retrieve customerOrderId auto-generated key");
            }

            return customerOrderId;
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem creating a new customer ", e);
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
            throw new RuntimeException("Encountered problem finding customer order id="+id, e);
        }
        if (result != null) {
            result.setCustomerOrderLineItems(lineItemDao.findByCustomerOrderId(result.getCustomerOrderId()));
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

    public void setLineItemDao(CustomerOrderLineItemDao lineItemDao) {
        this.lineItemDao = lineItemDao;
    }
}
