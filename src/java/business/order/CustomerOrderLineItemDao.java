package business.order;

import java.sql.Connection;
import java.util.List;

/**
 */
public interface CustomerOrderLineItemDao {
    void create(Connection connection, long customerOrderId, long productId, short quantity);
    List<CustomerOrderLineItem> findByCustomerOrderId(long customerOrderId);
}
