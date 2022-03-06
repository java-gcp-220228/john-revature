package com.revature.exception;

public class WrongAccountException extends Exception {
    public WrongAccountException() {
    }

    public WrongAccountException(String message) {
        super(message);
    }

    public WrongAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAccountException(Throwable cause) {
        super(cause);
    }

    public WrongAccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
