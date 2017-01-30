package business.customer;

import java.util.List;

/**
 */
public interface CustomerDao {
    Customer findByCustomerId(long customerId);

    List<Customer> findAll();
}
