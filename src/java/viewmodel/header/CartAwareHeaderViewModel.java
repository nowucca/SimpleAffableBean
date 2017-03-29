package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Any header view model that needs to know anything about the shopping cart
 * should extend this class.
 */
public class CartAwareHeaderViewModel {

    protected HttpServletRequest request;
    protected HttpSession session;

    protected ShoppingCart cart;

    CartAwareHeaderViewModel(HttpServletRequest request, ShoppingCart cart) {
        this.request = request;
        this.session = request.getSession(false);
        this.cart = cart;
    }



    public int getNumberOfItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }

    boolean getHasCart() {
        return this.cart != null;
    }

    boolean isRequestingPage(String method, String servletPath) {
        return method.equalsIgnoreCase(request.getMethod()) &&
            servletPath.equals(request.getServletPath());
    }
}
