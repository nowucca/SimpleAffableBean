package dao;

import entity.CustomerOrder;
import java.util.List;

/**
 */
public interface CustomerOrderDao {
    CustomerOrder findByCustomerId(long customerId);

    List<CustomerOrder> findAll();
}
