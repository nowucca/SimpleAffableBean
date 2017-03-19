package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class HeaderModel {

    private String relativeReturnUrl;

    public HeaderModel(HttpServletRequest request) {
        this.relativeReturnUrl = getRelativeReturnUrl(request);
    }

    public String getRelativeReturnUrl() {
        return relativeReturnUrl;
    }

    private String getRelativeReturnUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        return "/" + url.substring(baseURL.length());
    }

    // Good place to put support for common header and footer elements that are dynamic.
    // Also a good place to put elements onto a page that are generally useful
    // (e.g. XSRF tokens for cross-site scripting prevention)
}
