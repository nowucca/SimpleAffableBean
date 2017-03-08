package business.category;

import java.util.List;

/**
 */
public class DefaultCategoryService implements CategoryService {
    private CategoryDao categoryDao;

    @Override
    public Category findByCategoryId(long categoryId) {
        return categoryDao.findByCategoryId(categoryId);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
