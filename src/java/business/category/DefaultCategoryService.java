package business.category;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 */
public class DefaultCategoryService implements CategoryService {
    private static final long DEFAULT_CATEGORY_ID = 1L;

    private static final Logger logger =
        LoggerFactory.getLogger(DefaultCategoryService.class);


    private CategoryDao categoryDao;

    @Override
    public Category findByCategoryId(long categoryId) {
        try {
            return categoryDao.findByCategoryId(categoryId);
        } catch (Exception e) {
            logger.error("Trouble finding category {}", categoryId, e);
            throw e;
        }
    }

    @Override
    public List<Category> findAll() {
        try {
            return categoryDao.findAll();
        } catch (Exception e) {
            logger.error("Trouble finding all categories", e);
            throw e;
        }
    }

    @Override
    public Category getDefaultCategory() {
        try {
            return categoryDao.findByCategoryId(DEFAULT_CATEGORY_ID);
        } catch (Exception e) {
            logger.error("Trouble finding default category {}", DEFAULT_CATEGORY_ID, e);
            throw e;
        }
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }
}
