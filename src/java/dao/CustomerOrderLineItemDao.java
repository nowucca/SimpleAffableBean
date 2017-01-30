package dao;

import entity.CustomerOrderLineItem;
import java.util.List;

/**
 */
public interface CustomerOrderLineItemDao {
    List<CustomerOrderLineItem> findByCustomerOrderId(long customerOrderId);
}
