package business.product;

import java.util.List;

/**
 */
public interface ProductService {

    List<Product> findByCategoryId(long categoryId);

    Product findByProductId(long productId);
}
