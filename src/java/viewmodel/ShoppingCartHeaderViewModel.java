package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class ShoppingCartHeaderViewModel {

    protected HttpServletRequest request;

    ShoppingCartHeaderViewModel(HttpServletRequest request) {
        this.request = request;
    }

    public boolean getIsVisible() {
        return false;
    }
}
