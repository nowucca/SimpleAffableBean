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
package business.order;

import business.JdbcUtils;
import business.SimpleAffableDbException;
import business.ValidationException;
import business.cart.ShoppingCart;
import business.customer.Customer;
import business.customer.CustomerForm;
import business.customer.CustomerService;
import business.product.Product;
import business.product.ProductDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 */
public class DefaultCustomerOrderService implements CustomerOrderService {

    private CustomerOrderDao customerOrderDao;
    private CustomerOrderLineItemDao customerOrderLineItemDao;
    private CustomerService customerService;
    private ProductDao productDao;
    private Random random = new Random();


    private static final Logger logger =
        LoggerFactory.getLogger(DefaultCustomerOrderService.class);

    @Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) throws ValidationException {

        try {
            return performPlaceOrder(customerForm, cart);
        } catch (Exception e) {
            logger.error("Trouble placing an order.", e);
            throw e;
        }

    }

    private long performPlaceOrder(CustomerForm customerForm, ShoppingCart cart) throws ValidationException {
        try (Connection connection = JdbcUtils.getConnection()) {
            return performPlaceOrderTransaction(customerForm, cart, connection);
        } catch (SQLException e) {
            throw new SimpleAffableDbException("Error during close connection for customer order", e);
        }
    }

    private long performPlaceOrderTransaction(CustomerForm customerForm, ShoppingCart cart, Connection connection) throws SQLException, ValidationException {
        try {
            connection.setAutoCommit(false);

            long customerId = customerService.create(connection, customerForm);
            long customerOrderId = customerOrderDao.create(connection, customerId, cart.getTotal(), generateConfirmationNumber());


            cart.getItems().forEach((item) -> customerOrderLineItemDao.create(connection, customerOrderId, item.getProductId(), item.getQuantity()));

            connection.commit();
            return customerOrderId;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new SimpleAffableDbException("Failed to roll back transaction", e1);
            }
            throw e;
        }
    }

    private final Function<CustomerOrderLineItem, Product> LINE_ITEM_TO_PRODUCT =
        (lineItem) -> productDao.findByProductId(lineItem.getProductId());

    @Override
    public CustomerOrderDetails getOrderDetails(long customerOrderId) {

        try {
            CustomerOrder order = customerOrderDao.findByCustomerOrderId(customerOrderId);
            Customer customer = customerService.findByCustomerId(order.getCustomerId());
            List<CustomerOrderLineItem> lineItems = new ArrayList<>(customerOrderLineItemDao.findByCustomerOrderId(customerOrderId));
            List<Product> products = lineItems.stream().map(LINE_ITEM_TO_PRODUCT).collect(Collectors.toList());

            return new CustomerOrderDetails(order, customer, products, lineItems);
        } catch (Exception e) {
            logger.error("Trouble getting order details.", e);
            throw e;
        }
    }

    @Override
    public List<CustomerOrder> findAll() {
        return customerOrderDao.findAll().stream()
            .sorted(Comparator.comparing(CustomerOrder::getDateCreated))
            .collect(Collectors.toList());
    }

    @Override
    public CustomerOrder findByCustomerId(long customerId) {
        return customerOrderDao.findByCustomerId(customerId);
    }

    @Override
    public CustomerOrder findByCustomerOrderId(long customerOrderId) {
        return customerOrderDao.findByCustomerOrderId(customerOrderId);
    }

    private int generateConfirmationNumber() {
        return random.nextInt(999999999);
    }

    public void setCustomerOrderDao(CustomerOrderDao customerOrderDao) {
        this.customerOrderDao = customerOrderDao;
    }

    public void setCustomerOrderLineItemDao(CustomerOrderLineItemDao customerOrderLineItemDao) {
        this.customerOrderLineItemDao = customerOrderLineItemDao;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
