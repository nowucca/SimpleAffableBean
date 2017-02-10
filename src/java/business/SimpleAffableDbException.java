package business;

/**
 */
public class SimpleAffableDbException extends RuntimeException {

    public SimpleAffableDbException(String message) {
        super(message);
    }

    public SimpleAffableDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class SimpleAffableConnectionDbException extends SimpleAffableDbException {
        public SimpleAffableConnectionDbException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class SimpleAffableQueryDbException extends SimpleAffableDbException {
        public SimpleAffableQueryDbException(String message) {
            super(message);
        }

        public SimpleAffableQueryDbException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class SimpleAffableUpdateDbException extends SimpleAffableDbException {
        public SimpleAffableUpdateDbException(String message) {
            super(message);
        }

        public SimpleAffableUpdateDbException(String message, Throwable cause) {
            super(message, cause);
        }

    }
}
