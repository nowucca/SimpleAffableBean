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

    public CustomerOrderDetails(CustomerOrder customerOrder, Customer customer, List<Product> products) {
        this.customerOrder = customerOrder;
        this.customer = customer;
        this.products = products;
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
        return Collections.unmodifiableList(customerOrder.getCustomerOrderLineItems());
    }
}
