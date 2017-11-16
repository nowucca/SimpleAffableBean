package webservices;

import java.io.StringWriter;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Provider
@Priority(Priorities.USER)
public class WebServiceExceptionHandler implements ExceptionMapper<WebServiceException> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Response toResponse(WebServiceException exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof WebServiceException.AuthenticationFailure) {
            status = Response.Status.UNAUTHORIZED;
        } else if (exception instanceof WebServiceException.InvalidParameter) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof WebServiceException.NotFound) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof WebServiceException.NotAllowed) {
            status = Response.Status.FORBIDDEN;
        } else if (exception instanceof WebServiceException.PaymentNeeded) {
            status = Response.Status.PAYMENT_REQUIRED;
        } else if (exception instanceof WebServiceException.MaxQueueSizeExceeded) {
            status = Response.Status.CONFLICT;
        } else {
            logger.warn("Got an internal server error ", exception);
        }
        try {
            StringWriter writer = new StringWriter();
            writer.append(status.getReasonPhrase()).append(" ").append(String.valueOf(status.getStatusCode())).append("\n\n").append(exception.getMessage());
            return Response.status(status).entity(writer.getBuffer().toString()).type(MediaType.TEXT_PLAIN).build();
        } catch (Exception e) {
            logger.error("Problem attempting to map a PortalException to a json response", e);
            logger.error("Original PortalException is - ", exception);
            return Response.serverError().build();
        }
    }
}
