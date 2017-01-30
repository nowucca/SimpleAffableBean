package business.order;

import java.util.List;

/**
 */
public interface CustomerOrderLineItemDao {
    List<CustomerOrderLineItem> findByCustomerOrderId(long customerOrderId);
}
