package com.aerialarts.blog.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedRequestException extends BaseException {
    public UnauthorizedRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
