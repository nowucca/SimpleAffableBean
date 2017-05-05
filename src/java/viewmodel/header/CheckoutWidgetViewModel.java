/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
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
 */
public class CheckoutWidgetViewModel extends HeaderWidgetViewModel {

    protected ShoppingCart cart;
    private Boolean hasValidationErrorFlag;
    private Boolean hasOrderFailureFlag;

    CheckoutWidgetViewModel(HttpServletRequest request, ShoppingCart cart) {
        super(request);
        if (session != null) {
            this.hasValidationErrorFlag = (Boolean) session.getAttribute("validationErrorFlag");
            this.hasOrderFailureFlag = (Boolean) session.getAttribute("orderFailureFlag");
        }
        this.cart = cart;

    }

    @Override
    public boolean getIsVisible() {
        final boolean haveSomethingInYourCart = getHasCart() && getNumberOfItems() != 0;
        final boolean goingToCheckoutPath = "/checkout".equals(request.getServletPath());
        return haveSomethingInYourCart && !goingToCheckoutPath && !hasCheckoutErrors();
    }

    public int getNumberOfItems() {
        if (getHasCart()) {
            return cart.getNumberOfItems();
        } else {
            return 0;
        }

    }

    private boolean getHasCart() {
        return this.cart != null;
    }


    private boolean hasCheckoutErrors() {

        return (this.hasOrderFailureFlag != null && this.hasOrderFailureFlag) ||
            (this.hasValidationErrorFlag != null && this.hasValidationErrorFlag);
    }
}
