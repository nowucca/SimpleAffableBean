package controller; /**
 */

import business.ApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAffableBeanContextListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // Public constructor is required by servlet spec
    public SimpleAffableBeanContextListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed).
         You can initialize servlet context related data here.
      */
        logger.info("Servlet context initializing.");
        // initialize servlet with configuration information
        ApplicationContext applicationContext = ApplicationContext.INSTANCE;

        // store category list in servlet context
        final ServletContext servletContext = sce.getServletContext();
        if (servletContext.getAttribute("categories") == null) {
            servletContext.setAttribute("categories", applicationContext.getCategoryService().findAll());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context
         (the Web application) is undeployed or
         Application Server shuts down.
      */
        logger.info("Servlet context shutting down.");
        ApplicationContext.INSTANCE.shutdown();
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
      logger.debug("Created HttpSession {}", se.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
        logger.debug("Destroyed HttpSession {}", se.getSession().getId());
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is added to a session.
      */
      logger.debug("Adding session attribute {}={} for session {}", sbe.getName(), sbe.getValue(), sbe.getSession().getId());
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        logger.debug("Removing session attribute {}={} from session {}", sbe.getName(), sbe.getValue(), sbe.getSession().getId());
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
        logger.trace("Replacing session attribute value {}={} for session {}", sbe.getName(), sbe.getValue(), sbe.getSession().getId());
    }
}
