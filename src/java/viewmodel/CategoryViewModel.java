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
