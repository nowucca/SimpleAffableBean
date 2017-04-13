package business.customer;

import java.sql.Connection;
import java.util.List;

/**
 */
public interface CustomerService {
    long create(Connection connection, String name, String email,
                String phone, String address, String cityRegion, String
        ccNumber);

    Customer findByCustomerId(long customerId);

    List<Customer> findAll();
}
