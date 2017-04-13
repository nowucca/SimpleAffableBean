package business.category;

import business.SimpleAffableDbException.SimpleAffableQueryDbException;
import business.product.ProductDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CategoryDaoJdbc implements CategoryDao {

    private static final String FIND_ALL_SQL =
        "SELECT " +
            "c.category_id, " +
            "c.name " +
        "FROM " +
            "category c";

    private static final String FIND_BY_CATEGORY_ID_SQL =
        "SELECT " +
            "c.category_id, " +
            "c.name " +
        "FROM " +
            "category c " +
        "WHERE " +
            "c.category_id = ?";

    private ProductDao productDao;

    @Override
    public Category findByCategoryId(long categoryId) {
        Category result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {

             statement.setLong(1, categoryId);

             try (ResultSet resultSet = statement.executeQuery()) {
                 if (resultSet.next()) {
                     result = readCategory(resultSet);
                 }
             }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding category "+categoryId, e);
        }
        return result;
    }

    @Override
    public List<Category> findAll() {
        List<Category> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category c = readCategory(resultSet);
                result.add(c);
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem finding all categories", e);
        }
        return result;
    }

    private Category readCategory(ResultSet resultSet) throws SQLException {
        Long categoryId = resultSet.getLong("category_id");
        String categoryName = resultSet.getString("name");
        return new Category(categoryId, categoryName);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
