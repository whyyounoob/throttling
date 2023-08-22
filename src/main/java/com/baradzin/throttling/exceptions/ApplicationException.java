package com.baradzin.throttling.exceptions;


public abstract class ApplicationException extends RuntimeException {

    public ApplicationException() {
        super(null, null, false, false);
    }

    public ApplicationException(final String message) {
        super(message, null, false, false);
    }

    public abstract ErrorCode getErrorCode();

}
