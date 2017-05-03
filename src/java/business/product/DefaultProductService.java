package business.product;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class DefaultProductService implements ProductService {

    private static final Logger logger =
        LoggerFactory.getLogger(DefaultProductService.class);

    private ProductDao productDao;

    @Override
    public List<Product> findByCategoryId(long categoryId) {
        try {
            return productDao.findByCategoryId(categoryId);
        } catch (Exception e) {
            logger.error("Trouble finding product by category id {}", categoryId, e);
            throw e;
        }
    }

    @Override
    public Product findByProductId(long productId) {
        try {
            return productDao.findByProductId(productId);
        } catch (Exception e) {
            logger.error("Trouble finding product by product id {}", productId, e);
            throw e;
        }
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
