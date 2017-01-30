/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package entity;

import java.util.Collection;
import java.util.List;


/**
 *
 */
//@Entity
//@Table(name = "customer")
//@NamedQueries({
//    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
//    @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"),
//    @NamedQuery(name = "Customer.findByName", query = "SELECT c FROM Customer c WHERE c.name = :name"),
//    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
//    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
//    @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"),
//    @NamedQuery(name = "Customer.findByCityRegion", query = "SELECT c FROM Customer c WHERE c.cityRegion = :cityRegion"),
//    @NamedQuery(name = "Customer.findByCcNumber", query = "SELECT c FROM Customer c WHERE c.ccNumber = :ccNumber")})
public class Customer  {

    private long customerId;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String cityRegion;

    private String ccNumber;

    private List<CustomerOrder> customerOrders;

    public Customer(long customerId, String name, String email, String phone, String address, String cityRegion, String ccNumber) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.cityRegion = cityRegion;
        this.ccNumber = ccNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCityRegion() {
        return cityRegion;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString() {
        return "entity.Customer[customerId=" + customerId + "]";
    }

}
