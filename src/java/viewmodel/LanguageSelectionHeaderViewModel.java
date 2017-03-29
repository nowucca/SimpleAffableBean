package viewmodel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

/**
 */
public class LanguageSelectionHeaderViewModel {

    protected HttpServletRequest request;

    LanguageSelectionHeaderViewModel(HttpServletRequest request) {
        this.request = request;
    }

    public boolean getIsVisible() {
        // replaced: !fn:contains(pageContext.request.servletPath,'/confirmation') in JSP
        return !request.getServletPath().contains("/confirmation");
    }

}
