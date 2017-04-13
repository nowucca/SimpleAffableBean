/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import business.ApplicationContext;
import business.category.CategoryService;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import viewmodel.CategoryViewModel;

/**
 *
 */
@WebServlet(name = "Category",
            urlPatterns = {"/category"})
public class CategoryServlet extends SimpleAffableBeanServlet {

    private CategoryService categoryService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        categoryService = applicationContext.getCategoryService();
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


        // use RequestDispatcher to forward request internally
        request.setAttribute("p", new CategoryViewModel(request));
        doForwardToJSP(request, response, "/category");
    }



}
