package dao;

import entity.Category;
import entity.Customer;
import java.util.List;

/**
 */
public interface CustomerDao {
    Customer findByCustomerId(long customerId);

    List<Customer> findAll();
}
