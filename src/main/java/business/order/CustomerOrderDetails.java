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
package business.order;

import business.customer.Customer;
import business.product.Product;
import java.util.Collections;
import java.util.List;

/**
 */
public class CustomerOrderDetails {

    private CustomerOrder customerOrder;
    private Customer customer;
    private List<Product> products;
    private List<CustomerOrderLineItem> lineItems;

    public CustomerOrderDetails(CustomerOrder customerOrder, Customer customer, List<Product> products, List
        <CustomerOrderLineItem> lineItems) {
        this.customerOrder = customerOrder;
        this.customer = customer;
        this.products = products;
        this.lineItems = lineItems;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public List<CustomerOrderLineItem> getCustomerOrderLineItems() {
        return Collections.unmodifiableList(lineItems);
    }
}
