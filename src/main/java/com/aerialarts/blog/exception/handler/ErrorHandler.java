package com.aerialarts.blog.exception.handler;

import com.aerialarts.blog.exception.BaseException;
import com.aerialarts.blog.exception.InternalServerException;
import com.aerialarts.blog.exception.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        BaseException baseException;
        if (e instanceof BaseException) baseException = (BaseException) e;
        else
            baseException = new InternalServerException("Something went wrong. Please try again later.");
        return ResponseEntity.status(baseException.getStatus()).body(baseException.getErrorResponse());
    }
}
