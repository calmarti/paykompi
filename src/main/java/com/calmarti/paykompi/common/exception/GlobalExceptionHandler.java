package com.calmarti.paykompi.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorDetails> handleGeneric(Exception e, HttpServletRequest request){
        APIErrorDetails errorDetails = new APIErrorDetails(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
