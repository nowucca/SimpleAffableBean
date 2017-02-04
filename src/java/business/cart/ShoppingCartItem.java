/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.cart;

import business.product.Product;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {

    Product product;
    short quantity;

    public ShoppingCartItem(Product product) {
        this.product = product;
        quantity = 1;
    }

    public long getProductId() {
        return product.getProductId();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public short getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return quantity * getPrice();
    }
    public Product getProduct() {
        return product;
    }
}
