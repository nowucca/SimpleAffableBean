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
package webservices;

import business.ApplicationContext;
import business.JdbcUtils;
import business.ValidationException;
import business.category.Category;
import business.category.CategoryService;
import business.customer.Customer;
import business.customer.CustomerForm;
import business.customer.CustomerService;
import business.product.Product;
import business.product.ProductService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@ApplicationPath("/")
@Path("/")
public class WebServiceResource {

    private final ProductService productService = ApplicationContext.INSTANCE.getProductService();
    private final CategoryService categoryService = ApplicationContext.INSTANCE.getCategoryService();
    private final CustomerService customerService = ApplicationContext.INSTANCE.getCustomerService();

    @GET
    @Path("product/byId/{productId}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Product productByProductId(@PathParam("productId") long productId,
                        @Context HttpServletRequest httpRequest) {
        try {
            Product result = productService.findByProductId(productId);
            if (result == null) {
                throw new WebServiceException.NotFound(String.format("Product not found productId=%s", productId));
            }
            return result;
        } catch (WebServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new WebServiceException("product id lookup failed", e);
        }
    }


    @GET
    @Path("categories")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public List<Category> categories(@Context HttpServletRequest httpRequest) {
        try {
            return categoryService.findAll();
        } catch (Exception e) {
            throw new WebServiceException("categories lookup failed", e);
        }
    }

    @GET
    @Path("products/byCategoryName/{categoryName}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public List<Product> productsByCategoryName(@PathParam("categoryName") String  categoryName,
                              @Context HttpServletRequest httpRequest) {
        try {
            Category category = categoryService.findAll().stream()
                .filter((c) -> c.getName().toLowerCase().equals(categoryName))
                .findFirst().orElse(null);

            if (category == null) {
                throw new WebServiceException.NotFound(String.format("No such category: %s", categoryName));
            }

            return productService.findByCategoryId(category.getCategoryId());
        } catch (WebServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new WebServiceException("products lookup via categoryName failed", e);
        }
    }

    @PUT
    @Path("customer")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public Customer register(@FormParam("name") String name,
                             @FormParam("email") String email,
                             @FormParam("phone") String phone,
                             @FormParam("address") String address,
                             @FormParam("cityRegion") String cityRegion,
                             @FormParam("creditcard") String creditcard,
                             @Context HttpServletRequest request) {
        //
        // Because we use Connection in the service API it leaks up all the way here.
        //
        try (Connection connection = JdbcUtils.getConnection()) {
            CustomerForm customerForm = new CustomerForm(name, email, phone, address, cityRegion, creditcard);
            long customerId = customerService.create(connection, customerForm);
            return customerService.findByCustomerId(customerId);
        } catch (ValidationException e) {
            throw new WebServiceException.InvalidParameter("Invalid customer registration", e);
        } catch (SQLException e) {
            throw new WebServiceException("Error during close connection for customer order", e);
        } catch (Exception e) {
            throw new WebServiceException("Customer registration failed", e);
        }
    }
}
