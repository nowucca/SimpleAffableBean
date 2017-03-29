package viewmodel;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import static viewmodel.common.ShoppingCartViewCommon.initCartFromSession;

/**
 */
public class CartViewModel extends BaseViewModel {

    private ShoppingCart cart;

    public CartViewModel(HttpServletRequest request) {
        super(request);
        this.cart = initCartFromSession(session);
    }

    public int getNumberOfCartItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }

    public ShoppingCart getCart() {
        return cart;
    }

    public boolean getHasCart() {
        return this.cart != null;
    }

    public boolean getHasNonEmptyCart() {
        return this.cart != null && getNumberOfCartItems() > 0;
    }

}
