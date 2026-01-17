package com.ayoub.technicaltestuserapi.api;

import com.ayoub.technicaltestuserapi.metier.exceptions.EligibilityException;
import com.ayoub.technicaltestuserapi.metier.exceptions.UserAlreadyExistsException;
import com.ayoub.technicaltestuserapi.metier.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().isEmpty()
                ? "Invalid request body"
                : ex.getBindingResult().getFieldErrors().getFirst().getField() + ": " +
                ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserExist(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EligibilityException.class)
    public ResponseEntity<String> handleEligibilityException(EligibilityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidRequestBody(HttpMessageNotReadableException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Gender")) {
            return ResponseEntity.badRequest().body("Invalid gender. Allowed values: MALE, FEMALE, OTHER");
        }
        return ResponseEntity.badRequest().body("Invalid request body");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    }
}
