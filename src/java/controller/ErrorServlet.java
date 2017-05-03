package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viewmodel.ErrorViewModel;

@WebServlet(name = "ErrorServlet", urlPatterns = {"/Error"})
public class ErrorServlet extends SimpleAffableBeanServlet {

    private static final Logger logger = LoggerFactory.getLogger(ErrorServlet.class);

    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorViewModel errorViewModel = new ErrorViewModel(request);
        request.setAttribute("p", errorViewModel);

        logger.error("Site error {} @ {} (servlet={} msg={})",
            errorViewModel.getStatusCode(), errorViewModel.getRequestUri(),
            errorViewModel.getServletName(), errorViewModel.getMessage());

        final Integer statusCode = errorViewModel.getStatusCode();

        if (statusCode != null) {
            switch (statusCode) {
                case 403: {
                    doForwardToErrorJSPF(request, response, "/403");
                    break;
                }
                case 404: {
                    doForwardToErrorJSPF(request, response, "/404");
                    break;
                }
            }
        }
        doForwardToErrorJSPF(request, response, "/500");
    }

    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


    protected void doForwardToErrorJSPF(HttpServletRequest request, HttpServletResponse response, String jspfNameNoExtension) {
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/jspf/error" + jspfNameNoExtension + ".jspf";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
