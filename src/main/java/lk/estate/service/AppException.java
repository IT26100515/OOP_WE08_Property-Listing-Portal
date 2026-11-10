
package lk.estate.service;

/**
 * A user-correctable failure, with an HTTP status for the web layer.
 */
public class AppException extends RuntimeException {

    private final int status;

    public AppException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
