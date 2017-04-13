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
    private List<CustomerOrderLineItem> lineItems;

    public CustomerOrderDetails(CustomerOrder customerOrder, Customer customer, List<Product> products, List
        <CustomerOrderLineItem> lineItems) {
        this.customerOrder = customerOrder;
        this.customer = customer;
        this.products = products;
        this.lineItems = lineItems;
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
        return Collections.unmodifiableList(lineItems);
    }
}
