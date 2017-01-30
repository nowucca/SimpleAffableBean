/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package entity;

import java.util.List;

/**
 *
 */
//@Entity
//@Table(name = "category")
//@NamedQueries({
//    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
//    @NamedQuery(name = "Category.findById", query = "SELECT c FROM Category c WHERE c.id = :id"),
//    @NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")})
public class Category {

    private long categoryId;
    private String name;
    private List<Product> products;

    public Category(long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "entity.Category[id=" + categoryId + "]";
    }

}
