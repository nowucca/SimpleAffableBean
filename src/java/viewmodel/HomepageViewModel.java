package viewmodel;

import business.category.Category;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 */
@SuppressWarnings("unchecked")
public class HomepageViewModel extends BaseViewModel {

    List<Category> categories;

    public HomepageViewModel(HttpServletRequest request) {
        super(request);
        this.categories = (List<Category>) request.getServletContext().getAttribute("categories");
    }

    public List<Category> getCategories() {
        return categories;
    }

}
