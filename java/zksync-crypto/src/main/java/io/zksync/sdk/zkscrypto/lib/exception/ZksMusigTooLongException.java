package io.zksync.sdk.zkscrypto.lib.exception;

public class ZksMusigTooLongException extends Exception {
    public ZksMusigTooLongException() {
        super("Musig message is too long");
    }

    public ZksMusigTooLongException(String message) {
        super(message);
    }

    public ZksMusigTooLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZksMusigTooLongException(Throwable cause) {
        super(cause);
    }

    public ZksMusigTooLongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
