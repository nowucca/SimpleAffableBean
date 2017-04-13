/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.order;



/**
 *
 */
public class CustomerOrderLineItem {

    private long customerOrderId;
    private long productId;
    private short quantity;

    public CustomerOrderLineItem(long customerOrderId, long productId, short quantity) {
        this.customerOrderId = customerOrderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getCustomerOrderId() {
        return customerOrderId;
    }

    public long getProductId() {
        return productId;
    }

    public short getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("business.order.CustomerOrderLineItem[customerOrderId=%d,productId=%d]",
            customerOrderId, productId);
    }

}
