package org.juhewu.data.exception;

/**
 * 解密异常
 *
 * @author duanjw
 * @since 2022/09/27
 */
public class DecryptException extends RuntimeException {

    public DecryptException() {
    }

    public DecryptException(String message) {
        super(message);
    }

    public DecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptException(Throwable cause) {
        super(cause);
    }

    public DecryptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
