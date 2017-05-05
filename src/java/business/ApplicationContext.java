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
package business;

import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.category.CategoryService;
import business.category.DefaultCategoryService;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.customer.CustomerService;
import business.customer.DefaultCustomerService;
import business.order.CustomerOrderDao;
import business.order.CustomerOrderDaoJdbc;
import business.order.CustomerOrderLineItemDao;
import business.order.CustomerOrderLineItemDaoJdbc;
import business.order.CustomerOrderService;
import business.order.DefaultCustomerOrderService;
import business.product.DefaultProductService;
import business.product.ProductDao;
import business.product.ProductDaoJdbc;
import business.product.ProductService;

/**
 */
public final class ApplicationContext {

    private ProductService productService;

    private CategoryService categoryService;

    private CustomerService customerService;

    private CustomerOrderService customerOrderService;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

        // wire up the business.dao layer "by hand"
        ProductDao productDao = new ProductDaoJdbc();
        productService = new DefaultProductService();
        ((DefaultProductService) productService).setProductDao(productDao);

        CategoryDao categoryDao = new CategoryDaoJdbc();
        ((CategoryDaoJdbc) categoryDao).setProductDao(productDao);
        categoryService = new DefaultCategoryService();
        ((DefaultCategoryService) categoryService).setCategoryDao(categoryDao);

        CustomerDao customerDao = new CustomerDaoJdbc();
        customerService = new DefaultCustomerService();
        ((DefaultCustomerService) customerService).setCustomerDao(customerDao);

        CustomerOrderLineItemDao customerOrderLineItemDao = new CustomerOrderLineItemDaoJdbc();
        CustomerOrderDao customerOrderDao = new CustomerOrderDaoJdbc();
        ((CustomerOrderDaoJdbc) customerOrderDao).setLineItemDao(customerOrderLineItemDao);

        customerOrderService = new DefaultCustomerOrderService();
        DefaultCustomerOrderService service = (DefaultCustomerOrderService) customerOrderService;
        service.setProductDao(productDao);
        service.setCustomerDao(customerDao);
        service.setCustomerOrderDao(customerOrderDao);
        service.setCustomerOrderLineItemDao(customerOrderLineItemDao);
    }

    public ProductService getProductService() {
        return productService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public CustomerOrderService getCustomerOrderService() {
        return customerOrderService;
    }
}
