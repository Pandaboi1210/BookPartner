package com.sprint.BookPartnerApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice //used to handle the global exception                
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) //used to handle the speciifc exception
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return notFound(ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateResourceException ex) {
        return conflict(ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInput(InvalidInputException ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<String> handleResourceInUse(ResourceInUseException ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<String> handleEmployee(EmployeeException ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(JobsException.class)
    public ResponseEntity<String> handleJobs(JobsException ex) {
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {

        var fieldError = ex.getBindingResult().getFieldErrors().get(0);
        String message = fieldError.getDefaultMessage();
        Object rejectedValue = fieldError.getRejectedValue();

        // Append the rejected value so the user sees what they entered wrong
        if (rejectedValue != null && !rejectedValue.toString().isBlank()) {
            message += ". Received: " + rejectedValue;
        }

        return badRequest(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        Object value = ex.getValue();

        String hint;
        if ("Short".equalsIgnoreCase(requiredType)) {
            hint = paramName + " must be a valid number between 1 and 32767";
        } else if ("Integer".equalsIgnoreCase(requiredType)) {
            hint = paramName + " must be a valid integer";
        } else {
            hint = paramName + " must be a valid " + requiredType;
        }

        return badRequest(hint + ". Received: " + value);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return internalServerError("Something went wrong");
    }

    private ResponseEntity<String> badRequest(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    private ResponseEntity<String> notFound(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    private ResponseEntity<String> conflict(String message) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(message);
    }

    private ResponseEntity<String> internalServerError(String message) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }
}