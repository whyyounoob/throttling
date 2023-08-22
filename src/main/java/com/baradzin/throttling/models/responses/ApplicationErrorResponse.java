package com.baradzin.throttling.models.responses;

import lombok.Data;

@Data
public class ApplicationErrorResponse {

    private final int errorCode;
    private final String message;

    public ApplicationErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
