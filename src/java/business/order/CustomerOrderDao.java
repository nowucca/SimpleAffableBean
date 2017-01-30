package business.order;

import java.sql.Connection;
import java.util.List;

/**
 */
public interface CustomerOrderDao {
    long create(Connection connection, long customerId, int amount, int confirmationNumber);

    CustomerOrder findByCustomerId(long customerId);
    CustomerOrder findByCustomerOrderId(long customerOrderId);

    List<CustomerOrder> findAll();
}
