package io.matterlabs.zkscrypto.lib.exceiption;

public class ZksSeedTooShortException extends Exception {

    public ZksSeedTooShortException() {
        super("Given seed is too short, length must be greater than 32");
    }

    public ZksSeedTooShortException(String message) {
        super(message);
    }

    public ZksSeedTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZksSeedTooShortException(Throwable cause) {
        super(cause);
    }

    public ZksSeedTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
