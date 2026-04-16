package com.sprint.BookPartnerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> buildError(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("message", message);
        return error;
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // 409
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(
            DuplicateResourceException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    // 400
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInput(
            InvalidInputException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400
    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Map<String, Object>> handleResourceInUse(
            ResourceInUseException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            BadRequestException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Map<String, Object>> handleEmployee(
            EmployeeException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400
    @ExceptionHandler(JobsException.class)
    public ResponseEntity<Map<String, Object>> handleJobs(
            JobsException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // Fallback handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}