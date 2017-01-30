/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.cart;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCartItem {

    long productId;
    int price;
    short quantity;

    public ShoppingCartItem(long productId, int price) {
        this.productId = productId;
        this.price = price;
        quantity = 1;
    }

    public long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public short getQuantity() {
        return quantity;
    }




}
