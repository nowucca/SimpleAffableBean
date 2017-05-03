package viewmodel;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class ErrorViewModel extends BaseViewModel {
    private String servletName;
    private String requestUri;
    private Integer statusCode;
    private Throwable throwable;

    public ErrorViewModel(HttpServletRequest request) {
        super(request);
        // Analyze the servlet exception
        throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
    }

    public String getServletName() {
        return servletName;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getMessage() {
        if (throwable == null) {
            return "N/A";
        } else {
            return throwable.getMessage();
        }
    }
}
