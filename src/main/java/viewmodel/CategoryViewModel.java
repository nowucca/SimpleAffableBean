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
package viewmodel;

import business.category.Category;
import business.product.Product;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 */
@SuppressWarnings("unchecked")
public class CategoryViewModel extends BaseViewModel {

    private Category selectedCategory;
    private Collection<Product> selectedCategoryProducts;

    public CategoryViewModel(HttpServletRequest request) {
        super(request);

        HttpSession session = request.getSession();

        // get categoryId from request
        String categoryId = request.getQueryString();

        if (categoryId != null) {
            // get selected category
            selectedCategory = getCategoryService().findByCategoryId(Short.parseShort(categoryId));
            rememberSelectedCategory(session, selectedCategory);

            // get all products for selected category
            selectedCategoryProducts = getProductService().findByCategoryId(selectedCategory.getCategoryId());
            rememberSelectedCategoryProducts(session, selectedCategoryProducts);

        } else {
            // use the selected category from the session, otherwise use the default category
            selectedCategory = recallSelectedCategory(session);
            selectedCategoryProducts = recallSelectedCategoryProducts(session);
            if (selectedCategory == null || selectedCategoryProducts == null) {
                selectedCategory = getCategoryService().getDefaultCategory();
                rememberSelectedCategory(session, selectedCategory);
                selectedCategoryProducts = getProductService().findByCategoryId(selectedCategory.getCategoryId());
                rememberSelectedCategoryProducts(session, selectedCategoryProducts);
            }
        }
    }

    private Collection<Product> recallSelectedCategoryProducts(HttpSession session) {
        return (Collection<Product>) session.getAttribute("selectedCategoryProducts");
    }

    private Category recallSelectedCategory(HttpSession session) {
        return (Category) session.getAttribute("selectedCategory");
    }

    private void rememberSelectedCategory(HttpSession session, Category selectedCategory) {
        session.setAttribute("selectedCategory", selectedCategory);
    }

    private void rememberSelectedCategoryProducts(HttpSession session, Collection<Product> selectedCategoryProducts) {
        session.setAttribute("selectedCategoryProducts", selectedCategoryProducts);
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public Collection<Product> getSelectedCategoryProducts() {
        return selectedCategoryProducts;
    }
}
