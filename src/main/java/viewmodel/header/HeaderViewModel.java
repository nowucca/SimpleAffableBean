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
