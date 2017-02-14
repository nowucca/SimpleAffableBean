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
import business.category.CategoryDao;
import business.order.CustomerOrderDetails;
import business.order.CustomerOrderService;
import business.product.ProductDao;
import business.category.Category;
import business.product.Product;
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import validate.Validator;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "SimpleAffableBean",
            loadOnStartup = 1,
            urlPatterns = {"/chooseLanguage"})
public class SimpleAffableBeanServlet extends HttpServlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;

        // store category list in servlet context
        if (getServletContext().getAttribute("categories") == null) {
            getServletContext().setAttribute("categories", applicationContext.getCategoryDao().findAll());
        }
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
        HttpSession session = request.getSession();

        // if user switches language
        if (userPath.equals("/chooseLanguage")) {

            // get language choice
            String language = request.getParameter("language");

            // place in request scope
            request.setAttribute("language", language);

            String userView = (String) session.getAttribute("view");

            if ((userView != null) &&
                (!userView.equals("/index"))) {     // index.jsp exists outside 'view' folder
                                                    // so must be forwarded separately
                userPath = userView;
            } else {

                // if previous view is index or cannot be determined, send user to welcome page
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initHttpHeaders(req, resp);
        super.service(req, resp);
    }

    private void initHttpHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!allowBrowserCaching()) {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, no-store, private, must-revalidate, proxy-revalidate");
            response.setDateHeader("Expires", System.currentTimeMillis() - 86400000); // 1000 (millis) * 60 (seconds) * 60 (minutes) * 24 (hours) == 1 day -or- yesterday
        }
    }

    protected boolean allowBrowserCaching() {
        return true;
    }

    protected void doForwardToJSP(HttpServletRequest request, HttpServletResponse response, String userPath) {
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doTemporaryRedirect(HttpServletRequest request, HttpServletResponse response, String location) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);

        response.setHeader("Location", getServletContext().getContextPath()+location);
    }

}
