package com.calmarti.paykompi.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIErrorDetails> handleBadCredentials(BadCredentialsException e, HttpServletRequest request){
        APIErrorDetails errorDetails = new APIErrorDetails(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<APIErrorDetails> handleAccessDeniedException(CustomAccessDeniedException e, HttpServletRequest request){
        APIErrorDetails errorDetails = new APIErrorDetails(
                e.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Malformed request body",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorDetails> handleMethodArgumentNotValidBadRequest(MethodArgumentNotValidException e, HttpServletRequest request){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach((error)-> errors.put(error.getField(),error.getDefaultMessage()));


        APIErrorDetails errorDetails = new APIErrorDetails(
                "Request validation failed",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                errors
                );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    //handler for bad request of type MissingServletRequestParameterException.class



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorDetails> handleNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<APIErrorDetails> handleDuplicateException(DuplicateResourceException e, HttpServletRequest request){
        APIErrorDetails errorDetails = new APIErrorDetails(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorDetails> handleGeneric(Exception e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
