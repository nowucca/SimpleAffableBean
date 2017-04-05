package viewmodel.header;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 */
public abstract class HeaderWidgetViewModel {
    protected HttpServletRequest request;
    protected HttpSession session;

    HeaderWidgetViewModel(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession(false);
    }

    public abstract boolean getIsVisible();

}
