package com.baradzin.throttling.exceptions;


import com.baradzin.throttling.models.responses.ApplicationErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ResponseBody
    @ApiResponse(responseCode = "500",
            description = "Exception during request",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApplicationErrorResponse.class)
            ))
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApplicationErrorResponse handleApplicationException(final Exception e) {
        return handleException(e);
    }

    @ResponseBody
    @ApiResponse(responseCode = "400",
            description = "Invalid requests parameters",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApplicationErrorResponse.class)
            ))
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class, InvalidDataException.class})
    public ApplicationErrorResponse handleBadRequestException(final Exception e) {
        return handleException(e);
    }

    @ResponseBody
    @ApiResponse(responseCode = "429",
            description = "Too many requests",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApplicationErrorResponse.class)
            ))
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler({TooManyRequestsException.class})
    public ApplicationErrorResponse handleTooManyRequestException(final Exception e) {
        return handleException(e);
    }


    private ApplicationErrorResponse handleException(final Exception e) {

        final ErrorCode errorCode = getErrorCodeForException(e);
        final String message = StringUtils.isBlank(e.getMessage())
                ? errorCode.getMessage()
                : e.getMessage();
        logger.warn(message, e);
        return new ApplicationErrorResponse(errorCode.getErrorCode(), message);
    }

    private ErrorCode getErrorCodeForException(final Exception e) {
        if (e instanceof ApplicationException) {
            return ((ApplicationException) e).getErrorCode();
        } else {
            return ErrorCode.INTERNAL_SERVER;
        }
    }
}
