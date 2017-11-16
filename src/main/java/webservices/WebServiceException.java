package webservices;


/**
 *
 */
public class WebServiceException extends RuntimeException {

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class AuthenticationFailure extends WebServiceException {
        public AuthenticationFailure(String message) {
            super(message);
        }

        public AuthenticationFailure(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class InvalidParameter extends WebServiceException {
        public InvalidParameter(String message) {
            super(message);
        }

        public InvalidParameter(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotFound extends WebServiceException {
        public NotFound(String message) {
            super(message);
        }

        public NotFound(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class NotAllowed extends WebServiceException {
        public NotAllowed(String message) {
            super(message);
        }

        public NotAllowed(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class PaymentNeeded extends WebServiceException {
        public PaymentNeeded(String message) {
            super(message);
        }

        public PaymentNeeded(String message, Throwable t) {
            super(message, t);
        }
    }

    public static class MaxQueueSizeExceeded extends WebServiceException {
        public MaxQueueSizeExceeded(String message) {
            super(message);
        }

        public MaxQueueSizeExceeded(String message, Throwable t) {
            super(message, t);
        }
    }

}
