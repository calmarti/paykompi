package com.calmarti.paykompi.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record APIErrorDetails(
        String message,
        int status,
        String path,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
}
