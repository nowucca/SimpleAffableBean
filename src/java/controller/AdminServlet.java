/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package controller;

import business.customer.Customer;
import business.customer.CustomerDao;
import business.order.CustomerOrder;
import business.order.CustomerOrderDao;
import business.order.CustomerOrderDetails;
import business.order.CustomerOrderService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;


/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "AdminServlet",
            urlPatterns = {"/admin/",
                           "/admin/viewOrders",
                           "/admin/viewCustomers",
                           "/admin/customerRecord",
                           "/admin/orderRecord",
                           "/admin/logout"})
@ServletSecurity(
    @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = {"simpleAffableBeanAdmin"})
)
public class AdminServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;
        customerDao = applicationContext.getCustomerDao();
        customerOrderDao = applicationContext.getCustomerOrderDao();
        customerOrderService = applicationContext.getCustomerOrderService();
    }

    private CustomerDao customerDao;
    private CustomerOrderDao customerOrderDao;
    private CustomerOrderService customerOrderService;




    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String userPath = request.getServletPath();

        // if viewCustomers is requested
        if (userPath.equals("/admin/viewCustomers")) {
            List<Customer> customerList = customerDao.findAll();
            request.setAttribute("customerList", customerList);
        }

        // if viewOrders is requested
        if (userPath.equals("/admin/viewOrders")) {
            List<CustomerOrder> orderList = customerOrderDao.findAll();
            request.setAttribute("orderList", orderList);
        }

        // if customerRecord is requested
        if (userPath.equals("/admin/customerRecord")) {

            // get customer ID from request
            String customerId = request.getQueryString();

            // get customer details
            Customer customer = customerDao.findByCustomerId(Integer.parseInt(customerId));
            request.setAttribute("customerRecord", customer);

            // get customer order details
            CustomerOrder order = customerOrderDao.findByCustomerId(Integer.parseInt(customerId));
            request.setAttribute("order", order);
        }

        // if orderRecord is requested
        if (userPath.equals("/admin/orderRecord")) {

            // get customer ID from request
            String orderId = request.getQueryString();

            // get order details
            CustomerOrderDetails details = customerOrderService.getOrderDetails(Long.parseLong(orderId));

            // place order details in request scope
            request.setAttribute("customer", details.getCustomer());
            request.setAttribute("products", details.getProducts());
            request.setAttribute("orderRecord", details.getCustomerOrder());
            request.setAttribute("orderedProducts", details.getCustomerOrderLineItems());

        }

        // if logout is requested
        if (userPath.equals("/admin/logout")) {
            session = request.getSession();
            session.invalidate();   // terminate session
            response.sendRedirect(request.getContextPath() + "/admin/");
            return;
        }

        // use RequestDispatcher to forward request internally
        userPath = "/admin/index.jsp";
        try {
            request.getRequestDispatcher(userPath).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

}
