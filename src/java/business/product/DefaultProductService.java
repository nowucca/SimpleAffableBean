package business.product;

import java.util.List;

/**
 */
public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    @Override
    public List<Product> findByCategoryId(long categoryId) {
        return productDao.findByCategoryId(categoryId);
    }

    @Override
    public Product findByProductId(long productId) {
        return productDao.findByProductId(productId);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
