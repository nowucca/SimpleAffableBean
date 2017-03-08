package business.customer;

import java.sql.Connection;
import java.util.List;

/**
 */
public class DefaultCustomerService implements CustomerService {

    private CustomerDao customerDao;

    @Override
    public long create(Connection connection, String name, String email, String phone, String address, String cityRegion,
                       String ccNumber) {
        return customerDao.create(connection, name, email, phone, address, cityRegion, ccNumber);
    }

    @Override
    public Customer findByCustomerId(long customerId) {
        return customerDao.findByCustomerId(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
}
