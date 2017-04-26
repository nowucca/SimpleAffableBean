/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import business.ApplicationContext;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@WebServlet(name = "SimpleAffableBean",
            loadOnStartup = 1)
public class SimpleAffableBeanServlet extends HttpServlet {


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // initialize servlet with configuration information
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;

        // store category list in servlet context
        if (getServletContext().getAttribute("categories") == null) {
            getServletContext().setAttribute("categories", applicationContext.getCategoryService().findAll());
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set generic HTTP Headers that are useful for all requests.
        initHttpHeaders(req, resp);
        super.service(req, resp);
    }

    private void initHttpHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!allowBrowserCaching()) {
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache, no-store, private, must-revalidate, proxy-revalidate");
            response.setDateHeader("Expires", System.currentTimeMillis() - 86400000);
            // 1000 (millis) * 60 (seconds) * 60 (minutes) * 24 (hours) == 1 day -or- yesterday
        }
    }

    /**
     * Allow browser caching on all responses by default.
     * Some pages may override this when the page displays customer-specific information.
     *
     * @return true iff it is safe for the browser to cache information in the response
     */
    protected boolean allowBrowserCaching() {
        return true;
    }

    protected void doForwardToJSP(HttpServletRequest request, HttpServletResponse response, String userPath) {
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/jsp" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void doTemporaryRedirect(HttpServletRequest request, HttpServletResponse response, String location) {
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", getServletContext().getContextPath() + location);
    }


}
