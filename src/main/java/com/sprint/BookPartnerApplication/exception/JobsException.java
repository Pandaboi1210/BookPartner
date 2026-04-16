package com.sprint.BookPartnerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JobsException extends RuntimeException {

    public JobsException(String message) {
        super(message);
    }

    public JobsException(String message, Throwable cause) {
        super(message, cause);
    }
}