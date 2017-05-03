package business.customer;

import java.sql.Connection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 */
public class DefaultCustomerService implements CustomerService {

    private CustomerDao customerDao;

    private static final Logger logger =
        LoggerFactory.getLogger(DefaultCustomerService.class);

    @Override
    public long create(Connection connection, String name, String email,
                       String phone, String address, String cityRegion, String ccNumber) {
        try {
            return customerDao.create(connection, name, email, phone, address, cityRegion, ccNumber);
        } catch (Exception e) {
            logger.error("Trouble creating a customer.", e);
            throw e;
        }
    }

    @Override
    public Customer findByCustomerId(long customerId) {
        try {
            return customerDao.findByCustomerId(customerId);
        } catch (Exception e) {
            logger.error("Trouble finding customer {}", customerId, e);
            throw e;
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            return customerDao.findAll();
        } catch (Exception e) {
            logger.error("Trouble finding all customers.", e);
            throw e;
        }
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }
}
