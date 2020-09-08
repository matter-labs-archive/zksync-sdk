package io.matterlabs.zkscrypto.lib.exceiption;

public class ZksMusigTooLong extends Exception {
    public ZksMusigTooLong() {
        super("Musig message is too long");
    }

    public ZksMusigTooLong(String message) {
        super(message);
    }

    public ZksMusigTooLong(String message, Throwable cause) {
        super(message, cause);
    }

    public ZksMusigTooLong(Throwable cause) {
        super(cause);
    }

    public ZksMusigTooLong(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
