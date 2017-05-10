/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
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
package business.category;

import business.SimpleAffableDbException.SimpleAffableQueryDbException;
import business.product.ProductDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
            throw new SimpleAffableQueryDbException("Encountered problem finding category " + categoryId, e);
        }
        return result;
    }

    @Override
    public Collection<Category> findAll() {
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
