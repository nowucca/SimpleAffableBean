package viewmodel;

import business.ApplicationContext;
import business.cart.ShoppingCart;
import business.category.Category;
import business.category.CategoryService;
import business.order.CustomerOrderService;
import business.product.ProductService;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import viewmodel.header.HeaderViewModel;

/**
 * A base class for all view models for main pages.
 * Put access to data here that are potentially useful on all pages.
 * For example this is a good place to put support for common header
 * and footer elements that are dynamic.
 */
public class BaseViewModel {

    // The relative path to category images
    private static final String CATEGORY_IMAGE_PATH = "img/categories/";

    // The relative path to product images
    private static final String PRODUCT_IMAGE_PATH = "img/products/";

    // Every view model knows the request and session
    protected HttpServletRequest request;
    protected HttpSession session;

    // What do we know from the servlet context?
    private int deliverySurcharge;
    private List<Category> categories;

    // We also have a shopping cart and a header view model on each page.
    // Parts of the header may or may not be visible at different times.
    private ShoppingCart cart;
    private HeaderViewModel header;


    @SuppressWarnings("unchecked")
    public BaseViewModel(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession(false);
        this.cart = initCart();
        this.header = new HeaderViewModel(request, cart);
        this.categories = (List<Category>) request.getServletContext().getAttribute("categories");
        this.deliverySurcharge = Integer.valueOf(getDeliverySurchargeFromServletContext(request));
    }

    String getDeliverySurchargeFromServletContext(HttpServletRequest request) {
        return request.getServletContext().getInitParameter("deliverySurcharge");
    }

    private ShoppingCart initCart() {
        if (session != null) {
            final Object cart = session.getAttribute("cart");
            if (cart != null && cart instanceof ShoppingCart) {
                return (ShoppingCart) cart;
            }
        }
        return null;
    }

    public int getDeliverySurcharge() {
        return deliverySurcharge;
    }

    public String getCategoryImagePath() {
        return CATEGORY_IMAGE_PATH;
    }

    public String getProductImagePath() {
        return PRODUCT_IMAGE_PATH;
    }

    public HeaderViewModel getHeader() {
        return header;
    }


    //
    // Language-specific page elements
    //
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



    public List<Category> getCategories() {
        return categories;
    }
    public ShoppingCart getCart() {
        return cart;
    }

    public boolean getHasCart() {
        return this.cart != null;
    }

    public int getNumberOfCartItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }
    public boolean getHasNonEmptyCart() {
        return this.cart != null && getNumberOfCartItems() > 0;
    }

    public boolean getHasSelectedCategory() {
        return session.getAttribute("selectedCategory") != null;
    }
    protected CategoryService getCategoryService() {
        return ApplicationContext.INSTANCE.getCategoryService();
    }
    protected CustomerOrderService getCustomerOrderService() {
        return ApplicationContext.INSTANCE.getCustomerOrderService();
    }

    protected ProductService getProductService() {
        return ApplicationContext.INSTANCE.getProductService();
    }

    protected String getSessionAttributeOrRequestParameter(String name) {
        String v = String.class.cast(session.getAttribute(name));
        if (v == null) {
            v = request.getParameter(name);
        }
        return v;
    }

    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
