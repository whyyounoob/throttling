package com.baradzin.throttling.exceptions;

public class InvalidDataException extends ApplicationException {

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_DATA;
    }
}
