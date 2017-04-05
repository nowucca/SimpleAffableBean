package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

/**
 */
public class CheckoutWidgetViewModel extends HeaderWidgetViewModel {

    protected ShoppingCart cart;
    private Boolean hasValidationErrorFlag;
    private Boolean hasOrderFailureFlag;

    CheckoutWidgetViewModel(HttpServletRequest request, ShoppingCart cart) {
        super(request);
        if (session != null) {
            this.hasValidationErrorFlag = (Boolean) session.getAttribute("validationErrorFlag");
            this.hasOrderFailureFlag = (Boolean) session.getAttribute("orderFailureFlag");
        }
        this.cart = cart;

    }

    @Override
    public boolean getIsVisible() {
        final boolean haveSomethingInYourCart = getHasCart() && getNumberOfItems() != 0;
        final boolean goingToCheckoutPath = "/checkout".equals(request.getServletPath());
        return (haveSomethingInYourCart && !goingToCheckoutPath && !hasCheckoutErrors());
    }

    public int getNumberOfItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }

    private boolean getHasCart() {
        return this.cart != null;
    }


    private boolean hasCheckoutErrors() {

        return (this.hasOrderFailureFlag != null && this.hasOrderFailureFlag) ||
            (this.hasValidationErrorFlag != null && this.hasValidationErrorFlag);
    }
}
