package com.calmarti.paykompi.common.exception;

public class ExternalPaymentException extends RuntimeException {
    public ExternalPaymentException(String message) {
        super(message);
    }
}
