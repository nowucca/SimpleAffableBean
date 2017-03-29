package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

/**
 */
public class CheckoutHeaderViewModel extends CartAwareHeaderViewModel {

    private Boolean hasValidationErrorFlag;
    private Boolean hasOrderFailureFlag;

    CheckoutHeaderViewModel(HttpServletRequest request, ShoppingCart cart) {
        super(request, cart);
        this.hasValidationErrorFlag = (Boolean) session.getAttribute("validationErrorFlag");
        this.hasOrderFailureFlag = (Boolean) session.getAttribute("orderFailureFlag");
    }

    public boolean getIsViewable() {
        /*
        Replaces:
  <c:if test="${!empty cart && cart.numberOfItems != 0 &&

                                  !fn:contains(pageContext.request.servletPath,'/checkout') &&
                                  requestScope['javax.servlet.forward.servlet_path'] ne '/checkout' &&

                                  validationErrorFlag ne true &&
                                  orderFailureFlag ne true}">
         */
        final boolean haveSomethingInYourCart = getHasCart() && getNumberOfItems() != 0;
        final boolean goingToCheckoutPath = "/checkout".equals(request.getServletPath());
        return (haveSomethingInYourCart && !goingToCheckoutPath && !hasCheckoutErrors());
    }

    private boolean hasCheckoutErrors() {

        return (this.hasOrderFailureFlag != null && this.hasOrderFailureFlag) ||
            (this.hasValidationErrorFlag != null && this.hasValidationErrorFlag);
    }
}
