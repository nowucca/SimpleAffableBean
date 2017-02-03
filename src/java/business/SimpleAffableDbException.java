package business;

/**
 */
public class SimpleAffableDbException extends RuntimeException {
    public SimpleAffableDbException() {
    }

    public SimpleAffableDbException(String message) {
        super(message);
    }

    public SimpleAffableDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimpleAffableDbException(Throwable cause) {
        super(cause);
    }

    public SimpleAffableDbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
