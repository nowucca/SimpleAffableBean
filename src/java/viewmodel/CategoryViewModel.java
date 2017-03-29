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
            rememberSelectedCategory(session);

            // get all products for selected category
            selectedCategoryProducts = selectedCategory.getProducts();
            rememberSelectedCategoryProducts(session);

        } else {
            selectedCategory = (Category) session.getAttribute("selectedCategory");
            selectedCategoryProducts = (Collection<Product>) session.getAttribute("selectedCategoryProducts");
            if (selectedCategory == null || selectedCategoryProducts == null) {
                selectedCategory = getCategoryService().getDefaultCategory();
                rememberSelectedCategory(session);
                selectedCategoryProducts = selectedCategory.getProducts();
                rememberSelectedCategoryProducts(session);
            }
        }
    }

    private void rememberSelectedCategory(HttpSession session) {
        session.setAttribute("selectedCategory", selectedCategory);
    }

    private void rememberSelectedCategoryProducts(HttpSession session) {
        session.setAttribute("selectedCategoryProducts", selectedCategoryProducts);
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public Collection<Product> getSelectedCategoryProducts() {
        return selectedCategoryProducts;
    }
}
