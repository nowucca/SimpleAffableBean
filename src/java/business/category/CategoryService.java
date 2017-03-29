package business.category;

import java.util.List;

/**
 */
public interface CategoryService {
    Category findByCategoryId(long categoryId);

    List<Category> findAll();

    Category getDefaultCategory();

}
