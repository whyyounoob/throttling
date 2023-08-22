package com.baradzin.throttling.exceptions;


public enum ErrorCode {

    INTERNAL_SERVER(1, "Internal server error."),
    INVALID_DATA(2, "Invalid data."),
    TOO_MANY_REQUESTS(3, "Too many requests.");

    private final int errorCode;
    private final String message;

    ErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
