package com.example.BloggingApp.exception;

import com.example.BloggingApp.payload.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation Exception Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> responseError = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> responseError.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(responseError);
    }

    // Resource Not Found Exception Handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handelResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return new ResponseEntity<>(new ErrorDetailsDto(e.getMessage(), new Date(), webRequest.getDescription(true)), HttpStatus.NOT_FOUND);
    }
}