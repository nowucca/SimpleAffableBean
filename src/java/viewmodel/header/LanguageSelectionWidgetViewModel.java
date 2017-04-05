package viewmodel.header;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

/**
 */
public class LanguageSelectionWidgetViewModel extends HeaderWidgetViewModel {

    private String relativeReturnUrl;

    LanguageSelectionWidgetViewModel(HttpServletRequest request) {
        super(request);
        this.relativeReturnUrl = getRelativeReturnUrl(request);
    }

    @Override
    public boolean getIsVisible() {
        // replaced: !fn:contains(pageContext.request.servletPath,'/confirmation') in JSP
        return request.getServletPath() != null && !request.getServletPath().contains("/confirmation");
    }

    public String getRelativeReturnUrl() {
        return relativeReturnUrl;
    }

    private String getRelativeReturnUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return "/" + url.substring(baseURL.length());
    }

}
