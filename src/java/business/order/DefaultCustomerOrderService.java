package business.order;

import business.JdbcUtils;
import business.SimpleAffableDbException;
import business.ValidationException;
import business.cart.ShoppingCart;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.product.Product;
import business.product.ProductDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 */
public class DefaultCustomerOrderService implements CustomerOrderService {

    private CustomerOrderDao customerOrderDao;
    private CustomerOrderLineItemDao customerOrderLineItemDao;
    private CustomerDao customerDao;
    private ProductDao productDao;
    private Random random = new Random();


    private static final Logger logger =
        LoggerFactory.getLogger(DefaultCustomerOrderService.class);

    @Override
    public long placeOrder(String name, String email, String phone,
                           String address, String cityRegion, String ccNumber,
                           ShoppingCart cart) throws ValidationException {

        try {
            return performPlaceOrder(name, email, phone, address, cityRegion, ccNumber, cart);
        } catch (Exception e) {
            logger.error("Trouble placing an order.", e);
            throw e;
        }

    }

    private long performPlaceOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) throws ValidationException {
        validateForm(name, email, phone, address, cityRegion, ccNumber);

        try (Connection connection = JdbcUtils.getConnection()) {
            return performPlaceOrderTransaction(name, email, phone, address, cityRegion, ccNumber, cart, connection);
        } catch (SQLException e) {
            throw new SimpleAffableDbException("Error during close connection for customer order", e);
        }
    }

    private long performPlaceOrderTransaction(String name, String email, String phone, String address, String cityRegion,
                                              String ccNumber, ShoppingCart cart, Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);

            long customerId = customerDao.create(connection, name, email, phone, address, cityRegion, ccNumber);
            long customerOrderId = customerOrderDao.create(connection, customerId,
                cart.getTotal(), generateConfirmationNumber());


            cart.getItems().forEach((item) ->
                customerOrderLineItemDao.create(connection, customerOrderId, item.getProductId(), item.getQuantity()));

            connection.commit();
            return customerOrderId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new SimpleAffableDbException("Failed to roll back transaction", e1);
            }
            throw e;
        }
    }

    private final Function<CustomerOrderLineItem, Product> LINE_ITEM_TO_PRODUCT =
        (lineItem) -> productDao.findByProductId(lineItem.getProductId());

    @Override
    public CustomerOrderDetails getOrderDetails(long customerOrderId) {

        try {
            CustomerOrder order = customerOrderDao.findByCustomerOrderId(customerOrderId);
            Customer customer = customerDao.findByCustomerId(order.getCustomerId());
            List<CustomerOrderLineItem> lineItems = customerOrderLineItemDao.findByCustomerOrderId(customerOrderId);
            List<Product> products = lineItems.stream().map(LINE_ITEM_TO_PRODUCT).collect(Collectors.toList());

            return new CustomerOrderDetails(order, customer, products, lineItems);
        } catch (Exception e) {
            logger.error("Trouble getting order details.", e);
            throw e;
        }
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderDao.findAll();
    }

    @Override
    public CustomerOrder findByCustomerId(long customerId) {
        return customerOrderDao.findByCustomerId(customerId);
    }

    @Override
    public CustomerOrder findByCustomerOrderId(long customerOrderId) {
        return customerOrderDao.findByCustomerOrderId(customerOrderId);
    }

    private void validateForm(String name, String email, String phone,
                              String address, String cityRegion, String ccNumber)
        throws ValidationException {

        ValidationException e = new ValidationException();


        if (name == null
            || name.equals("")
            || name.length() > 45) {
            e.fieldError("name");
        }
        if (email == null
            || email.equals("")
            || !email.contains("@")) {
            e.fieldError("email");
        }
        if (phone == null
            || phone.equals("")
            || phone.length() < 9) {
            e.fieldError("phone");
        }
        if (address == null
            || address.equals("")
            || address.length() > 45) {
            e.fieldError("address");
        }
        if (cityRegion == null
            || cityRegion.equals("")
            || cityRegion.length() > 2) {
            e.fieldError("cityRegion");
        }
        if (ccNumber == null
            || ccNumber.equals("")
            || ccNumber.length() > 19) {
            e.fieldError("ccNumber");
        }

        if (e.hasErrors()) {
            throw e;
        }

    }

    private int generateConfirmationNumber() {
        return random.nextInt(999999999);
    }

    public void setCustomerOrderDao(CustomerOrderDao customerOrderDao) {
        this.customerOrderDao = customerOrderDao;
    }

    public void setCustomerOrderLineItemDao(CustomerOrderLineItemDao customerOrderLineItemDao) {
        this.customerOrderLineItemDao = customerOrderLineItemDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
