package com.calmarti.paykompi.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorDetails> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Malformed request body or not existent",
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    //handler for bad request of type MissingServletRequestParameterException.class
    //handler for bad request of type MethodArgumentNotValidException.class (validations)


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorDetails> handleNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Not found",
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<APIErrorDetails> handleDuplicateException(DuplicateResourceException e, HttpServletRequest request){
        APIErrorDetails errorDetails = new APIErrorDetails(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorDetails> handleGeneric(Exception e, HttpServletRequest request) {
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
