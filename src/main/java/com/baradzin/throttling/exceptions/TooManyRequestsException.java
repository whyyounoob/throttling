package com.baradzin.throttling.exceptions;

public class TooManyRequestsException extends ApplicationException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.TOO_MANY_REQUESTS;
    }
}
