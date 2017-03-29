package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 * The current homepage shows categories and a header.
 * Because categories are shared amongst pages, this view model is rather empty currently.
 */
@SuppressWarnings("unchecked")
public class HomepageViewModel extends BaseViewModel {

    public HomepageViewModel(HttpServletRequest request) {
        super(request);
    }

}
