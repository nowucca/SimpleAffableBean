package business;

import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.CustomerOrderDao;
import business.order.CustomerOrderDaoJdbc;
import business.order.CustomerOrderLineItemDao;
import business.order.CustomerOrderLineItemDaoJdbc;
import business.order.CustomerOrderService;
import business.order.DefaultCustomerOrderService;
import business.product.ProductDao;
import business.product.ProductDaoJdbc;

/**
 */
public class ApplicationContext {

    private ProductDao productDao;
    private CategoryDao categoryDao;
    private CustomerDao customerDao;
    private CustomerOrderDao customerOrderDao;
    private CustomerOrderLineItemDao customerOrderLineItemDao;

    private CustomerOrderService customerOrderService;


    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

        // wire up the business.dao layer "by hand"
        productDao = new ProductDaoJdbc();
        categoryDao = new CategoryDaoJdbc();
        ((CategoryDaoJdbc) categoryDao).setProductDao(productDao);

        customerDao = new CustomerDaoJdbc();

        customerOrderLineItemDao = new CustomerOrderLineItemDaoJdbc();
        customerOrderDao = new CustomerOrderDaoJdbc();
        ((CustomerOrderDaoJdbc) customerOrderDao).setLineItemDao(customerOrderLineItemDao);

        customerOrderService = new DefaultCustomerOrderService();
        DefaultCustomerOrderService service = (DefaultCustomerOrderService) customerOrderService;
        service.setProductDao(productDao);
        service.setCustomerDao(customerDao);
        service.setCustomerOrderDao(customerOrderDao);
        service.setCustomerOrderLineItemDao(customerOrderLineItemDao);
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }

    public CustomerOrderDao getCustomerOrderDao() {
        return customerOrderDao;
    }

    public CustomerOrderService getCustomerOrderService() {
        return customerOrderService;
    }
}
