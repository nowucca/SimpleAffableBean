package business.order;

import business.SimpleAffableDbException;
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
public class CustomerOrderLineItemDaoJdbc implements CustomerOrderLineItemDao {

    private static final String CREATE_LINE_ITEM_SQL =
        "INSERT INTO customer_order_line_item (customer_order_id, product_id, quantity) " +
         "VALUES (?, ?, ?)";

    private static final String FIND_BY_CUSTOMER_ORDER_ID_SQL =
        "SELECT " +
            "li.customer_order_id, li.product_id, li.quantity " +
        "FROM " +
            "customer_order_line_item li " +
        "WHERE " +
            "li.customer_order_id = ?";


    @Override
    public void create(Connection connection, long customerOrderId, long productId, short quantity) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_LINE_ITEM_SQL)) {
            statement.setLong(1, customerOrderId);
            statement.setLong(2, productId);
            statement.setShort(3, quantity);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new SimpleAffableUpdateDbException("Failed to insert an order line item, affected row count = "+affected);
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem creating a new customer ", e);
        }
    }

    @Override
    public List<CustomerOrderLineItem> findByCustomerOrderId(long customerOrderId) {
        List<CustomerOrderLineItem> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ORDER_ID_SQL)) {
            statement.setLong(1, customerOrderId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(readCustomerOrderLineItem(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding customer order line items for customer order "
                +customerOrderId, e);
        }
        return result;
    }


    private CustomerOrderLineItem readCustomerOrderLineItem(ResultSet resultSet) throws SQLException {
       Long customerOrderId = resultSet.getLong("customer_order_id");
       Long productId = resultSet.getLong("product_id");
       Short quantity = resultSet.getShort("quantity");
       return new CustomerOrderLineItem(customerOrderId, productId, quantity);
    }

}
