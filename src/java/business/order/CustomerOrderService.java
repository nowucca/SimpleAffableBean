package business.order;

import business.ValidationException;
import business.cart.ShoppingCart;
import java.util.List;

/**
 */
public interface CustomerOrderService {

    long placeOrder(String name, String email, String phone,
                    String address, String cityRegion, String ccNumber, ShoppingCart cart) throws ValidationException;

    CustomerOrderDetails getOrderDetails(long customerOrderId);

    List<CustomerOrder> findAll();

    CustomerOrder findByCustomerId(long customerId);

    CustomerOrder findByCustomerOrderId(long customerOrderId);
}
