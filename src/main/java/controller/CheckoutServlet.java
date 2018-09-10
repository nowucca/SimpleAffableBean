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
package controller;

import business.ApplicationContext;
import business.ValidationException;
import business.cart.ShoppingCart;
import business.customer.CustomerForm;
import business.order.CustomerOrderService;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import viewmodel.CheckoutViewModel;

/**
 *
 */
@WebServlet(name = "Checkout",
            urlPatterns = {"/checkout"})
public class CheckoutServlet extends SimpleAffableBeanServlet {

    private String surcharge;

    private CustomerOrderService customerOrderService;


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");

        customerOrderService = ApplicationContext.INSTANCE.getCustomerOrderService();

    }


    // do not cache the checkout details in the browser - we want always want to display the latest checkout details
    protected boolean allowBrowserCaching() {
        return false;
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        // checkout page is requested

        // forward to checkout page and switch to a secure channel

        // use RequestDispatcher to forward request internally
        request.setAttribute("p", new CheckoutViewModel(request));
        doForwardToJSP(request, response, userPath);
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
                                                // 8-bit Unicode (e.g., for Czech characters)

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart != null) {

            // extract user data from request
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String cityRegion = request.getParameter("cityRegion");
            String creditcard = request.getParameter("creditcard");

            // otherwise, save order to database
            try {
                CustomerForm customerForm = new CustomerForm(name, email, phone, address, cityRegion, creditcard);

                long orderId = customerOrderService.placeOrder(customerForm, cart);

                // if order processed successfully send user to confirmation page

                // in case language was set using toggle, get language choice before destroying session
                Locale locale = (Locale) session.getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");

                // dissociate shopping cart from session
                cart = null;

                // clear session
                forgetSession(session);

                if (locale != null) {
                    // if user changed language using the toggle,
                    // reset the language attribute
                    Config.set(session, Config.FMT_LOCALE, locale);
                    response.setLocale(locale);
                }

                // place order id in the fresh session scope
                session.setAttribute("customerOrderId", orderId);

                userPath = "/confirmation";


            } catch (ValidationException e) {
                // send back to checkout page and display validation error

                // remember which fields were in error and that we have an error
                session.setAttribute("validationException", e);
                session.setAttribute("validationErrorFlag", true);
                //remember the form inputs for redisplay except for credit card
                rememberSession(session, name, email, phone, address, cityRegion);
                userPath = "/checkout";
            } catch (Exception e) {
                // send back to checkout page and display general error

                // remember that we have an error
                session.setAttribute("orderFailureFlag", true);

                //remember the form inputs for redisplay except for credit card
                rememberSession(session, name, email, phone, address, cityRegion);
                userPath = "/checkout";
            }
        }

        // use RequestDispatcher to redirect request externally
        doTemporaryRedirect(request, response, userPath);
    }

    private void forgetSession(HttpSession session) {
        // Wipe out the cart explicitly
        session.setAttribute("cart", null);
        // Wipe out all other session attributes for a clean slate
        final Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            session.setAttribute(attributeNames.nextElement(), null);
        }
    }

    private void rememberSession(HttpSession session, String name, String email,
                                 String phone, String address, String cityRegion) {
        session.setAttribute("name", name);
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("address", address);
        session.setAttribute("cityRegion", cityRegion);
    }

}
