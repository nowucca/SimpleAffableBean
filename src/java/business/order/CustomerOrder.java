/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.order;

import java.util.Date;
import java.util.List;


/**
 *
 */
//@Entity
//@Table(name = "customer_order")
//@NamedQueries({
//    @NamedQuery(name = "CustomerOrder.findAll", query = "SELECT c FROM CustomerOrder c"),
//    @NamedQuery(name = "CustomerOrder.findById", query = "SELECT c FROM CustomerOrder c WHERE c.orderId = :orderId"),
//    @NamedQuery(name = "CustomerOrder.findByCustomer", query = "SELECT c FROM CustomerOrder c WHERE c.customer = :customer"), // manually created
//    @NamedQuery(name = "CustomerOrder.findByAmount", query = "SELECT c FROM CustomerOrder c WHERE c.amount = :amount"),
//    @NamedQuery(name = "CustomerOrder.findByDateCreated", query = "SELECT c FROM CustomerOrder c WHERE c.dateCreated = :dateCreated"),
//    @NamedQuery(name = "CustomerOrder.findByConfirmationNumber", query = "SELECT c FROM CustomerOrder c WHERE c.confirmationNumber = :confirmationNumber")})
public class CustomerOrder {

    private long customerOrderId;
    private long customerId;
    private int amount;
    private Date dateCreated;
    private int confirmationNumber;
    private List<CustomerOrderLineItem> customerOrderLineItems;


    public CustomerOrder() {
    }


    public CustomerOrder(long customerOrderId, long customerId, int amount, Date dateCreated, int confirmationNumber) {
        this.customerOrderId = customerOrderId;
        this.customerId = customerId;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.confirmationNumber = confirmationNumber;
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public List<CustomerOrderLineItem> getCustomerOrderLineItems() {
        return customerOrderLineItems;
    }

    public void setCustomerOrderLineItems(List<CustomerOrderLineItem> customerOrderLineItems) {
        this.customerOrderLineItems = customerOrderLineItems;
    }

    @Override
    public String toString() {
        return "business.order.CustomerOrder[customerOrderId=" + customerOrderId + "]";
    }

}
