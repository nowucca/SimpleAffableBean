package business.product;

import business.SimpleAffableDbException;
import business.SimpleAffableDbException.SimpleAffableQueryDbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class ProductDaoJdbc implements ProductDao {

    private static final String FIND_BY_CATEGORY_SQL =
        "SELECT " +
            "p.product_id, " +
            "p.category_id, " +
            "p.name, " +
            "p.price, " +
            "p.last_update " +
        "FROM " +
            "product p " +
        "WHERE " +
            "p.category_id = ?";

    private static final String FIND_BY_PRODUCT_ID_SQL =
        "SELECT " +
            " p.product_id, " +
            " p.category_id, " +
            " p.name, " +
            " p.price, " +
            " p.last_update " +
        "FROM " +
            " product p " +
        "WHERE " +
            "p.product_id = ?";


    @Override
    public List<Product> findByCategoryId(long categoryId) {
        List<Product> result = new ArrayList<>(16);

        try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(readProduct(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem reading products by category", e);
        }

        return result;
    }

    @Override
    public Product findByProductId(long productId) {
        Product result = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PRODUCT_ID_SQL)) {
            statement.setLong(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new SimpleAffableQueryDbException("Encountered problem reading products by product id", e);
        }

        return result;
    }

    private Product readProduct(ResultSet resultSet) throws SQLException {
        Product result;
        Long productIdFromDb = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        Date lastUpdate = resultSet.getTimestamp("last_update");

        result = new Product(productIdFromDb, name, price, lastUpdate);
        return result;
    }
}
