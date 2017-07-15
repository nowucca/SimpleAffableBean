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
package viewmodel;

import business.ApplicationContext;
import business.customer.CustomerService;
import business.order.CustomerOrderService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * A base class for all view models for admin pages.
 * Put access to data here that are potentially useful on all pages.
 * For example this is a good place to put support for common header
 * and footer elements that are dynamic.
 */
public class BaseAdminViewModel {

    // The relative path to product images
//    private static final String PRODUCT_IMAGE_PATH = "/img/products/";
    private static final String PRODUCT_IMAGE_PATH = BaseViewModel.PRODUCT_IMAGE_PATH;

    // Every view model knows the request and session
    protected HttpServletRequest request;
    protected HttpSession session;

    // All customer/order pages need the following service objects
    protected CustomerService customerService;
    protected CustomerOrderService customerOrderService;

    // Delivery surcharge
    protected int deliverySurcharge;

    @SuppressWarnings("unchecked")
    public BaseAdminViewModel(HttpServletRequest request) {
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        customerService = applicationContext.getCustomerService();
        customerOrderService = applicationContext.getCustomerOrderService();

        this.request = request;
        this.session = request.getSession(true);
        this.deliverySurcharge = Integer.valueOf(request.getServletContext().getInitParameter("deliverySurcharge"));
    }

    public HttpSession getSession() {
        return session;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public CustomerOrderService getCustomerOrderService() {
        return customerOrderService;
    }

    public String getProductImagePath() {
        return PRODUCT_IMAGE_PATH;
    }

    public int getDeliverySurcharge() {
        return deliverySurcharge;
    }

}

