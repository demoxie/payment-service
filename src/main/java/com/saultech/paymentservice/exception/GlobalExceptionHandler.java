package com.saultech.paymentservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIError> handleExceptions(APIException ex, HttpServletRequest request, HttpServletResponse response){
        String path = request.getContextPath();
        String errors = ex.getLocalizedMessage();
        APIError errorResponse =  APIError.builder()
                .status(httpStatusToString(ex.getStatus()))
                .message(ex.getMessage())
                .path(path)
                .errors(errors)
                .timestamp(Instant.now().toString())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIError> handleMethodArgumentNotValidException(ConstraintViolationException ex, HttpServletRequest request, HttpServletResponse response){
        String path = request.getContextPath();
        String errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        APIError errorResponse =  APIError.builder()
                .status(httpStatusToString(HttpStatus.BAD_REQUEST))
                .message(ex.getMessage())
                .path(path)
                .errors(errors+"Hello")
                .timestamp(Instant.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response){
        String path = request.getContextPath();
        String errors = ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        APIError errorResponse =  APIError.builder()
                .status(httpStatusToString(HttpStatus.BAD_REQUEST))
                .message(errors)
                .path(path)
                .errors(errors)
                .timestamp(Instant.now().toString())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



    private String httpStatusToString(HttpStatus status){
        return String.valueOf(status.value());
    }
}
