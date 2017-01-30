package by.belhostel.hostels.exception;

/**
 * Created by Roman on 29.01.2017.
 */
public class NoSuchTypeException extends Exception {
    public NoSuchTypeException() {
    }

    public NoSuchTypeException(String message) {
        super(message);
    }

    public NoSuchTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new no such type exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public NoSuchTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
