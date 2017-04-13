package business.order;

import business.ApplicationContext;
import business.IntegrationTestPlatform;
import business.SimpleAffableDbException;
import business.cart.ShoppingCart;
import business.product.ProductService;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static business.JdbcUtils.getConnection;
import static org.junit.jupiter.api.Assertions.*;

/**
 */
public class CustomerOrderServiceTest extends IntegrationTestPlatform {


    private static final int MILK_PRODUCT_ID = 1;
    private CustomerOrderService orderService;
    private ProductService productService;


    @BeforeEach
    public void setupBusinessObjects() throws Exception {
        orderService = ApplicationContext.INSTANCE.getCustomerOrderService();
        productService = ApplicationContext.INSTANCE.getProductService();
    }

    private static final String FIND_CUSTOMER_ORDER_BY_ID_SQL =
    "select customer_order_id, customer_id, amount, date_created, confirmation_number" +
        " from customer_order where customer_order_id = ?";

    private static final String FIND_CUSTOMER_BY_EMAIL =
        "select customer_id, name, email, phone, address, city_region, cc_number" +
            " from customer where email = ?";

    private static final String FIND_CUSTOMER_ORDER_LINE_BY_ID =
        "select customer_order_id, product_id, quantity " +
            " from customer_order_line_item where customer_order_id = ? ";


    @Test
    public void canPlaceValidOrder() throws Exception {
        final String validName = "validName";
        String validEmail = "valid@email.com";
        String validPhone = "4088675309";
        String validAddress = "123 Main St";
        String validCityRegion = "1";
        String validCCNumber = "4444333322221111";
        ShoppingCart validCart = new ShoppingCart();
        validCart.addItem(productService.findByProductId(MILK_PRODUCT_ID));
        validCart.calculateTotal("300");
        long customerOrderId = orderService.placeOrder(validName, validEmail, validPhone,
            validAddress, validCityRegion, validCCNumber, validCart);

        assertObjectStoredByKey(FIND_CUSTOMER_BY_EMAIL, validEmail, JDBCType.VARCHAR,
            () -> String.format("Could not locate customer by email %s", validEmail), (ResultSet resultSet) -> {

            try {
                Long dbCustomerId = resultSet.getLong("customer_id");
                String dbCustomerName = resultSet.getString("name");
                String dbCustomerEmail = resultSet.getString("email");
                String dbCustomerPhone = resultSet.getString("phone");
                String dbCustomerAddress = resultSet.getString("address");
                String dbCustomerCityRegion = resultSet.getString("city_region");
                String dbCustomerCCNumber = resultSet.getString("cc_number");

                assertAll("Customer read looks valid",
                    () -> assertNotNull(dbCustomerId, "Missing customerId"),
                    () -> assertEquals(validName, dbCustomerName, "Mismatched customer name"),
                    () -> assertEquals(validEmail, dbCustomerEmail, "Mismatched email"),
                    () -> assertEquals(validPhone, dbCustomerPhone, "Mismatched phone"),
                    () -> assertEquals(validAddress, dbCustomerAddress, "Mismatched address"),
                    () -> assertEquals(validCityRegion, dbCustomerCityRegion, "Mismatched city region"),
                    () -> assertEquals(validCCNumber, dbCustomerCCNumber, "Mismatched cc number"));
            } catch (SQLException e) {
                throw new SimpleAffableDbException("Customer email assertions failed with a SQL exception", e);
            }
        });

        assertObjectStoredById(FIND_CUSTOMER_ORDER_BY_ID_SQL, customerOrderId,
            () -> String.format("Could not locate customer order %d", customerOrderId), (ResultSet resultSet) -> {
            try {
                Long actualCustomerOrderId = resultSet.getLong("customer_order_id");
                Long customerId = resultSet.getLong("customer_id");
                int amount = resultSet.getInt("amount");
                Date dateCreated = resultSet.getTimestamp("date_created");
                Date now = new Date();
                int confirmationNumber = resultSet.getInt("confirmation_number");

                assertAll("Customer Order read looks valid",
                    () -> assertNotNull(customerOrderId, "Missing customerOrderId"),
                    () -> assertEquals(470, amount, "Expected amount to be price of milk plus surcharge"),
                    () -> assertTrue(now.getTime() - dateCreated.getTime()
                        < TimeUnit.SECONDS.toMillis(30), "Expected created timestamp close to the current time"),
                    () -> assertTrue(confirmationNumber > 0, "Valid positive confirmation number"));
            } catch (SQLException e) {
                throw new SimpleAffableDbException("Assertions failed with a SQL exception", e);
            }
        });

    }

    private void assertObjectStoredById(String byIdSql, long objectId,
                                        Supplier<String> failedToReadObject, Consumer<ResultSet> assertions)
        throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(byIdSql)) {
            statement.setLong(1, objectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    fail(failedToReadObject);
                } else {
                    assertions.accept(resultSet);
                }
            }
        }
    }

    private void assertObjectStoredByKey(String byKeySql, Object key, SQLType sqlType,
                                         Supplier<String> failedToReadObject,
                                         Consumer<ResultSet> assertions) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(byKeySql)) {
            statement.setObject(1, key, sqlType);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    fail(failedToReadObject);
                } else {
                    assertions.accept(resultSet);
                }
            }
        }
    }

}
