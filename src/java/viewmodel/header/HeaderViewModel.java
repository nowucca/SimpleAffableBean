package viewmodel.header;

import business.cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;

/**
 * Models all elements of the header.
 */
public class HeaderViewModel {

    private LanguageSelectionWidgetViewModel languageSelectionWidgetViewModel;
    private ShoppingCartWidgetViewModel shoppingCartWidgetViewModel;
    private CheckoutWidgetViewModel checkoutWidgetViewModel;

    public HeaderViewModel(HttpServletRequest request, ShoppingCart cart) {
        this.languageSelectionWidgetViewModel = new LanguageSelectionWidgetViewModel(request);
        this.shoppingCartWidgetViewModel = new ShoppingCartWidgetViewModel(request, cart);
        this.checkoutWidgetViewModel = new CheckoutWidgetViewModel(request, cart);
    }

    public LanguageSelectionWidgetViewModel getLanguageSelectionHeader() {
        return languageSelectionWidgetViewModel;
    }

    public ShoppingCartWidgetViewModel getShoppingCartHeader() {
        return shoppingCartWidgetViewModel;
    }

    public CheckoutWidgetViewModel getCheckoutHeader() {
        return checkoutWidgetViewModel;
    }

}
