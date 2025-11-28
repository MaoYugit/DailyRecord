package com.timeshards.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> handleException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(500, e.getMessage());
    }
}
