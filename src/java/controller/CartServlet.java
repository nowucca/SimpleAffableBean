/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import business.ApplicationContext;
import business.cart.ShoppingCart;
import business.product.Product;
import business.product.ProductDao;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
@WebServlet(name = "Cart",
            urlPatterns = {"/cart"})
public class CartServlet extends SimpleAffableBeanServlet {

    private ProductDao productDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        productDao = applicationContext.getProductDao();

        // store category list in servlet context
        getServletContext().setAttribute("categories", applicationContext.getCategoryDao().findAll());
    }

    // do not cache the cart contents in the browser - we want to display the latest cart always
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

        HttpSession session = request.getSession();

        // if cart page is requested
        String clear = request.getParameter("clear");

        if ((clear != null) && clear.equals("true")) {

            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            cart.clear();
        }
        doForwardToJSP(request, response, "/cart");
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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        // if addToCart action is called
        if ("add".equals(action)) {

            // if user is adding item to cart for first time
            // create cart object and attach it to user session
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            String productId = request.getParameter("productId");

            if (!productId.isEmpty()) {
                final int id = Integer.parseInt(productId);
                Product product = productDao.findByProductId(id);
                cart.addItem(product);
            }
            userPath = "/category";

        // if updateCart action is called
        } else if ("update".equals(action)) {

            // get input from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            try {
                cart.update(productDao.findByProductId(Integer.parseInt(productId)), Short.parseShort(quantity));
            } catch (Exception e) {
                // no message was sent to user in affable bean project
            }


            userPath = "/cart";
        }

        // use RequestDispatcher to redirect request externally
        doTemporaryRedirect(request, response, userPath);
    }

}