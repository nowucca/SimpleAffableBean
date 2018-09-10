/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2018 Steven Atkinson <steven@nowucca.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package business.product;

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
