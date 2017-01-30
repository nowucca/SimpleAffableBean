package business.order;

import java.util.List;

/**
 */
public interface CustomerOrderDao {
    CustomerOrder findByCustomerId(long customerId);

    List<CustomerOrder> findAll();
}
