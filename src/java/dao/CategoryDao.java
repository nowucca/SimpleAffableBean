package dao;

import entity.Category;
import java.util.List;

/**
 */
public interface CategoryDao {
    Category findByCategoryId(long categoryId);

    List<Category> findAll();
}
