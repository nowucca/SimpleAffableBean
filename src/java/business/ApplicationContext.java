package business;

import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.category.CategoryService;
import business.category.DefaultCategoryService;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.customer.CustomerService;
import business.customer.DefaultCustomerService;
import business.order.CustomerOrderDao;
import business.order.CustomerOrderDaoJdbc;
import business.order.CustomerOrderLineItemDao;
import business.order.CustomerOrderLineItemDaoJdbc;
import business.order.CustomerOrderService;
import business.order.DefaultCustomerOrderService;
import business.product.DefaultProductService;
import business.product.ProductDao;
import business.product.ProductDaoJdbc;
import business.product.ProductService;

/**
 */
public final class ApplicationContext {

    private ProductService productService;

    private CategoryService categoryService;

    private CustomerService customerService;

    private CustomerOrderService customerOrderService;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {

        // wire up the business.dao layer "by hand"
        ProductDao productDao = new ProductDaoJdbc();
        productService = new DefaultProductService();
        ((DefaultProductService) productService).setProductDao(productDao);

        CategoryDao categoryDao = new CategoryDaoJdbc();
        ((CategoryDaoJdbc) categoryDao).setProductDao(productDao);
        categoryService = new DefaultCategoryService();
        ((DefaultCategoryService) categoryService).setCategoryDao(categoryDao);

        CustomerDao customerDao = new CustomerDaoJdbc();
        customerService = new DefaultCustomerService();
        ((DefaultCustomerService) customerService).setCustomerDao(customerDao);

        CustomerOrderLineItemDao customerOrderLineItemDao = new CustomerOrderLineItemDaoJdbc();
        CustomerOrderDao customerOrderDao = new CustomerOrderDaoJdbc();
        ((CustomerOrderDaoJdbc) customerOrderDao).setLineItemDao(customerOrderLineItemDao);

        customerOrderService = new DefaultCustomerOrderService();
        DefaultCustomerOrderService service = (DefaultCustomerOrderService) customerOrderService;
        service.setProductDao(productDao);
        service.setCustomerDao(customerDao);
        service.setCustomerOrderDao(customerOrderDao);
        service.setCustomerOrderLineItemDao(customerOrderLineItemDao);
    }

    public ProductService getProductService() {
        return productService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public CustomerOrderService getCustomerOrderService() {
        return customerOrderService;
    }
}
