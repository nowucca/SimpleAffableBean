package business.order;

import business.cart.ShoppingCart;

/**
 */
public interface CustomerOrderService {

    long placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart);

    CustomerOrderDetails getOrderDetails(long customerOrderId);
}
