package com.calmarti.paykompi.common.exception;

import java.time.LocalDateTime;

public record APIErrorDetails(
        String message,
        int status,
        String path,
        LocalDateTime timestamp) {
}
