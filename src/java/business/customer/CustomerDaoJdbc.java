package business.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CustomerDaoJdbc implements CustomerDao {

    private static final String FIND_ALL_SQL =
        "SELECT " +
            "c.customer_id, c.name, c.email, " +
            "c.phone, c.address, c.city_region, c.cc_number " +
        "FROM " +
            "customer c";

    private static final String FIND_BY_CUSTOMER_ID_SQL =
        "SELECT " +
            "c.customer_id, c.name, c.email, " +
            "c.phone, c.address, c.city_region, c.cc_number " +
        "FROM " +
            "customer c " +
        "WHERE " +
            "c.customer_id = ?";


    @Override
    public Customer findByCustomerId(long customerId) {
        Customer result = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CUSTOMER_ID_SQL)) {
            statement.setLong(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = readCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem finding customer "+customerId, e);
        }
        return result;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new ArrayList<>(16);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            Customer c = readCustomer(resultSet);
            result.add(c);
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem finding all categories", e);
        }

        return result;
    }

    private Customer readCustomer(ResultSet resultSet) throws SQLException {
       Long customerId = resultSet.getLong("customer_id");
       String name = resultSet.getString("name");
       String email = resultSet.getString("email");
       String phone = resultSet.getString("phone");
       String address = resultSet.getString("address");
       String cityRegion = resultSet.getString("city_region");
       String cc_number = resultSet.getString("cc_number");
       return new Customer(customerId, name, email, phone, address, cityRegion, cc_number);
    }
}
