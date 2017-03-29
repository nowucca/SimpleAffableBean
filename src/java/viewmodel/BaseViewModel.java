package viewmodel;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

/**
 */
public class BaseViewModel {

    private HeaderViewModel header;

    // The relative path to category images
    private static final String categoryImagePath = "img/categories/";

    // The relative path to product images
    private static final String productImagePath = "img/products/";


    protected HttpServletRequest request;

    public BaseViewModel(HttpServletRequest request) {
        this.request = request;
        this.header = new HeaderViewModel(request);
    }

    public String getCategoryImagePath() {
        return categoryImagePath;
    }

    public String getProductImagePath() {
        return productImagePath;
    }

    public HeaderViewModel getHeader() {
        return header;
    }


    public boolean getHasCustomerSpecifiedLocale() {
        // replaced:  <c:when test="${empty sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}">
        return null != Config.get(request.getSession(), Config.FMT_LOCALE);
    }

    public boolean getIsRequestLocaleCzech() {
        // replaced: ${pageContext.request.locale.language ne 'cs'}
        return "cs".equals(request.getLocale().getLanguage());
    }

    public boolean isCustomerSpecifiedLocaleCzech() {
        // replaces: ${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] eq 'cs'
        Locale sessionLocale = (Locale) Config.get(request.getSession(), Config.FMT_LOCALE);
        return sessionLocale != null && "cs".equals(sessionLocale.getLanguage());

    }

    // Good place to put support for common header and footer elements that are dynamic.
    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
