package business.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static business.JdbcUtils.getConnection;

/**
 */
public class CustomerDaoJdbc implements CustomerDao {

    private static final String CREATE_CUSTOMER_SQL =
        "INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

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
    public long create(final Connection connection, String name, String email, String phone, String address, String cityRegion, String ccNumber) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, cityRegion);
            statement.setString(6, ccNumber);
            int affected = statement.executeUpdate();
            if (affected != 1) {
                throw new RuntimeException("Failed to insert a customer, affected row count = "+affected);
            }
            long customerId;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customerId = rs.getLong(1);
            } else {
                throw new RuntimeException("Failed to retrieve customerId auto-generated key");
            }

            return customerId;
        } catch (SQLException e) {
            throw new RuntimeException("Encountered problem creating a new customer ", e);
        }
    }

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
