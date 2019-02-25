package com.github.devcat24.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomIntlSvrError extends RuntimeException {
    public CustomIntlSvrError(String message) {
        super(message);
    }
}
