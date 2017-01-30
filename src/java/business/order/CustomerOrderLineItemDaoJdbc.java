package business.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CustomerOrderLineItemDaoJdbc implements CustomerOrderLineItemDao {


    private static final String FIND_BY_CUSTOMER_ORDER_ID_SQL =
        "SELECT " +
            "li.customer_order_id, li.product_id, li.quantity " +
        "FROM " +
            "customer_order_line_item li " +
        "WHERE " +
            "li.customer_order_id = ?";


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
            throw new RuntimeException("Encountered problem finding customer "+customerOrderId, e);
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
