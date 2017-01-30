/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html
 */

package session;

import business.order.CustomerOrderLineItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tgiunipero
 */
@Stateless
public class OrderedProductFacade extends AbstractFacade<CustomerOrderLineItem> {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderedProductFacade() {
        super(CustomerOrderLineItem.class);
    }

    // manually created
    public List<CustomerOrderLineItem> findByOrderId(Object id) {
        return em.createNamedQuery("CustomerOrderLineItem.findByCustomerOrderId").setParameter("customerOrderId", id).getResultList();
    }

}
