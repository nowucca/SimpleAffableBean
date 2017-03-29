package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class CheckoutHeaderViewModel {

    protected HttpServletRequest request;

    CheckoutHeaderViewModel(HttpServletRequest request) {
        this.request = request;
    }

    public boolean getIsVisible() {
        return false;
    }
}
