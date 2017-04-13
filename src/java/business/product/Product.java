/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.product;

import java.util.Date;

/**
 *
 */
public class Product {

    private long productId;
    private String name;
    private int price;
    private Date lastUpdate;

    public Product(long productId, String name, int price, Date lastUpdate) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    public long getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return "business.product.Product[product_id=" + productId + "]";
    }

}
