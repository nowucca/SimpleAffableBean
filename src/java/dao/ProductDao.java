package dao;

import entity.Product;
import java.util.List;

/**
 */
public interface ProductDao {
    List<Product> findByCategoryId(long categoryId);

    Product findByProductId(long productId);
}
