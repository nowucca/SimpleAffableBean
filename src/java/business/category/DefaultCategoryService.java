package business.category;

import java.util.List;

/**
 */
public class DefaultCategoryService implements CategoryService {
    private static final long DEFAULT_CATEGORY_ID = 1L;

    private CategoryDao categoryDao;

    @Override
    public Category findByCategoryId(long categoryId) {
        return categoryDao.findByCategoryId(categoryId);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category getDefaultCategory() {
        return categoryDao.findByCategoryId(DEFAULT_CATEGORY_ID);
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
