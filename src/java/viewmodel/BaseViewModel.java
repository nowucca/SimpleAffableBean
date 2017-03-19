package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class BaseViewModel {

    private HeaderModel header;

    // The relative path to category images
    private static final String categoryImagePath = "img/categories/";

    // The relative path to product images
    private static final String productImagePath = "img/products/";


    protected HttpServletRequest request;

    public BaseViewModel(HttpServletRequest request) {
        this.request = request;
        this.header = new HeaderModel(request);
    }

    public String getCategoryImagePath() {
        return categoryImagePath;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public HeaderModel getHeader() {
        return header;
    }

    // Good place to put support for common header and footer elements that are dynamic.
    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
