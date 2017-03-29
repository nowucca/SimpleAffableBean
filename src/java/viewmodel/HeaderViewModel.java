package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class HeaderViewModel {

    private String relativeReturnUrl;
    private LanguageSelectionHeaderViewModel languageSelectionHeaderViewModel;
    private ShoppingCartHeaderViewModel shoppingCartHeaderViewModel;
    private CheckoutHeaderViewModel checkoutHeaderViewModel;

    public HeaderViewModel(HttpServletRequest request) {
        this.relativeReturnUrl = getRelativeReturnUrl(request);
        this.languageSelectionHeaderViewModel = new LanguageSelectionHeaderViewModel(request);
        this.shoppingCartHeaderViewModel = new ShoppingCartHeaderViewModel(request);
        this.checkoutHeaderViewModel = new CheckoutHeaderViewModel(request);
    }

    public LanguageSelectionHeaderViewModel getLanguageSelectionHeader() {
        return languageSelectionHeaderViewModel;
    }

    public ShoppingCartHeaderViewModel getShoppingCartHeader() {
        return shoppingCartHeaderViewModel;
    }

    public CheckoutHeaderViewModel getCheckoutHeader() {
        return checkoutHeaderViewModel;
    }

    public String getRelativeReturnUrl() {
        return relativeReturnUrl;
    }

    private String getRelativeReturnUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return "/" + url.substring(baseURL.length());
    }

    // Good place to put support for common header and footer elements that are dynamic.
    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
