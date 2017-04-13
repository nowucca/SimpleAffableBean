/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package business.cart;

import business.ValidationException;
import business.product.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tgiunipero
 */
public class ShoppingCart {

    List<ShoppingCartItem> items;
    int numberOfItems;
    int total;

    public ShoppingCart() {
        items = new ArrayList<>();
        numberOfItems = 0;
        total = 0;
    }

    /**
     * Adds a <code>ShoppingCartItem</code> to the <code>ShoppingCart</code>'s
     * <code>items</code> list. If item of the specified <code>product</code>
     * already exists in shopping cart list, the quantity of that item is
     * incremented, and the original price remains unchanged.
     *
     * @see ShoppingCartItem
     */
    public synchronized void addItem(Product product) {

        boolean newItem = true;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProductId() == product.getProductId()) {

                newItem = false;
                scItem.quantity++;
            }
        }

        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }


    /**
     * Updates the <code>ShoppingCartItem</code> of the specified
     * <code>product</code> to the specified quantity. If '<code>0</code>'
     * is the given quantity, the <code>ShoppingCartItem</code> is removed
     * from the <code>ShoppingCart</code>'s <code>items</code> list.
     *
     * @param quantity the number which the <code>ShoppingCartItem</code> is updated to
     * @see ShoppingCartItem
     */
    public synchronized void update(Product product , short quantity) throws ValidationException {

        validateQuantity(quantity);

        ShoppingCartItem item = null;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProductId() == product.getProductId()) {

                if (quantity != 0) {
                    // set item quantity to new value
                    scItem.quantity = quantity;
                } else {
                    // if quantity equals 0, save item and break
                    item = scItem;
                    break;
                }
            }
        }

        if (item != null) {
            // remove from cart
            items.remove(item);
        }
    }

    // ensures that quantity input is number between 0 and 99
    // applies to quantity fields in cart page
    void validateQuantity (int quantity) throws ValidationException {
        if (quantity < 0 || quantity > 99) {
            throw new ValidationException("quantity");
        }
    }


    /**
     * Returns the list of <code>ShoppingCartItems</code>.
     *
     * @return the <code>items</code> list
     * @see ShoppingCartItem
     */
    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    /**
     * Returns the sum of quantities for all items maintained in shopping cart
     * <code>items</code> list.
     *
     * @return the number of items in shopping cart
     * @see ShoppingCartItem
     */
    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    /**
     * Returns the sum of the product price multiplied by the quantity for all
     * items in shopping cart list. This is the total cost excluding the surcharge.
     *
     * @return the cost of all items times their quantities
     * @see ShoppingCartItem
     */
    public synchronized int getSubtotal() {

        int amount = 0;

        for (ShoppingCartItem scItem : items) {

            amount += (scItem.getQuantity() * scItem.getPrice());
        }

        return amount;
    }

    /**
     * Calculates the total cost of the order. This method adds the subtotal to
     * the designated surcharge and sets the <code>total</code> instance variable
     * with the result.
     *
     * @param surcharge the designated surcharge for all orders
     * @see ShoppingCartItem
     */
    public synchronized void calculateTotal(String surcharge) {

        int amount = 0;

        amount = this.getSubtotal();
        amount +=  Integer.parseInt(surcharge);

        total = amount;
    }

    /**
     * Returns the total cost of the order for the given
     * <code>ShoppingCart</code> instance.
     *
     * @return the cost of all items times their quantities plus surcharge
     */
    public synchronized int getTotal() {

        return total;
    }

    /**
     * Empties the shopping cart. All items are removed from the shopping cart
     * <code>items</code> list, <code>numberOfItems</code> and
     * <code>total</code> are reset to '<code>0</code>'.
     *
     * @see ShoppingCartItem
     */
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }

}
