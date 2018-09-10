/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
    protected static final String PRODUCT_IMAGE_PATH = "img/products/";

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

    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
