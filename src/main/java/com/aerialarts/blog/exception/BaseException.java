package com.aerialarts.blog.exception;

import com.aerialarts.blog.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    private ErrorResponse errorResponse;


    public BaseException(String message) {
        super(message);
        errorResponse = new ErrorResponse(message);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public abstract HttpStatus getStatus();
}
