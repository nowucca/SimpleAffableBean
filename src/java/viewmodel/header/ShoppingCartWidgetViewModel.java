package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

/**
 */
public class ShoppingCartWidgetViewModel extends HeaderWidgetViewModel {

    protected ShoppingCart cart;

    ShoppingCartWidgetViewModel(HttpServletRequest request, ShoppingCart cart) {
        super(request);
        this.cart = cart;
    }


    public int getNumberOfItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }

    public boolean getHasCart() {
        return this.cart != null;
    }

    @Override
    public boolean getIsVisible() {
        return getHasCart() && getNumberOfItems() != 0 && !("/cart".equals(this.request.getServletPath()));
    }


    public String getItemsTextKey() {
        String result;

        switch (getNumberOfItems()) {
            case 1: {
                result = "item";
                break;
            }
            case 2:
            case 3:
            case 4: {
                result = "items2-4";
                break;
            }
            default:
                result = "items";
                break;
        }
        return result;
    }
}
