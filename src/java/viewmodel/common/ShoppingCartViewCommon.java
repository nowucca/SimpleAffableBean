package viewmodel.common;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpSession;

/**
 * Common View Model code associated with shopping carts.
 * Mainly useful for supporting common code in header widgets and the cart page.
 */
public class ShoppingCartViewCommon {

    public static ShoppingCart initCartFromSession(HttpSession session) {
        if (session != null) {
            final Object cart = session.getAttribute("cart");
            if (cart != null && cart instanceof ShoppingCart) {
                return (ShoppingCart) cart;
            }
        }
        return null;
    }
}
