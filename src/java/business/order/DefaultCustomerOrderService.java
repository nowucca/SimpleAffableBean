package business.order;

import business.JdbcUtils;
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

/**
 */
public class DefaultCustomerOrderService implements CustomerOrderService {

    private CustomerOrderDao customerOrderDao;
    private CustomerOrderLineItemDao customerOrderLineItemDao;
    private CustomerDao customerDao;
    private ProductDao productDao;
    private Random random = new Random();

    @Override
    public long placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {
        Connection connection = JdbcUtils.getConnection();
        try {
            connection.setAutoCommit(false);

            long customerId = customerDao.create(connection, name, email, phone, address, cityRegion, ccNumber);
            long customerOrderId = customerOrderDao.create(connection, customerId, cart.getTotal(), generateConfirmationNumber());

            cart.getItems().forEach((item) -> {
                customerOrderLineItemDao.create(connection, customerOrderId, item.getProductId(), item.getQuantity());
            });

            connection.commit();
            return customerOrderId;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException("Failed to roll back transaction", e1);
            }
            return 0;
        }

    }

    private final Function<CustomerOrderLineItem, Product> LINE_ITEM_TO_PRODUCT =
        (lineItem) -> productDao.findByProductId(lineItem.getProductId());

    @Override
    public CustomerOrderDetails getOrderDetails(long customerOrderId) {

        CustomerOrder order = customerOrderDao.findByCustomerOrderId(customerOrderId);
        Customer customer = customerDao.findByCustomerId(order.getCustomerId());
        List<Product> products = order.getCustomerOrderLineItems().stream()
            .map(LINE_ITEM_TO_PRODUCT)
            .collect(Collectors.toList());

        return new CustomerOrderDetails(order, customer, products);

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
