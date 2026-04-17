package com.sprint.BookPartnerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Common error builder
    private Map<String, Object> buildError(HttpStatus status, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("message", message);
        return error;
    }

    // 404 - Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    // 409 - Duplicate Resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(
            DuplicateResourceException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.CONFLICT, ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    // 400 - Invalid Input
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidInput(
            InvalidInputException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400 - Resource In Use
    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<Map<String, Object>> handleResourceInUse(
            ResourceInUseException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400 - Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            BadRequestException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400 - Employee Exception
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Map<String, Object>> handleEmployee(
            EmployeeException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400 - Jobs Exception
    @ExceptionHandler(JobsException.class)
    public ResponseEntity<Map<String, Object>> handleJobs(
            JobsException ex) {

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    // 400 - Validation Errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return new ResponseEntity<>(
                buildError(HttpStatus.BAD_REQUEST, errorMessage),
                HttpStatus.BAD_REQUEST);
    }

    // 500 - Unexpected Errors (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(
            Exception ex) {

        // Print full stack trace in console
        ex.printStackTrace();

        return new ResponseEntity<>(
                buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}