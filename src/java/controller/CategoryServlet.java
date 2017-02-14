/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import business.ApplicationContext;
import business.category.Category;
import business.category.CategoryDao;
import business.product.Product;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 */
@WebServlet(name = "Category",
            urlPatterns = {"/category"})
public class CategoryServlet extends SimpleAffableBeanServlet {

    private CategoryDao categoryDao;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        categoryDao = applicationContext.getCategoryDao();
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
        Category selectedCategory;
        Collection<Product> categoryProducts;

        // get categoryId from request
        String categoryId = request.getQueryString();

        if (categoryId != null) {

            // get selected category
            selectedCategory = categoryDao.findByCategoryId(Short.parseShort(categoryId));

            // place selected category in session scope
            session.setAttribute("selectedCategory", selectedCategory);

            // get all products for selected category
            categoryProducts = selectedCategory.getProducts();

            // place category products in session scope
            session.setAttribute("categoryProducts", categoryProducts);
        }

        // use RequestDispatcher to forward request internally
        doForwardToJSP(request, response, userPath);
    }



}
