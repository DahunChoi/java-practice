package com.example.javabasicsapi.common.exception;

import org.springframework.validation.FieldError;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        List<FieldError> errors,
        String path,
        OffsetDateTime timestamp
) {
    public static ErrorResponse of(ErrorCode errorCode, String message, List<FieldError> errors, String path) {
        return new ErrorResponse(
                errorCode.code(),
                message,
                errors,
                path,
                OffsetDateTime.now()
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return of(errorCode, message, List.of(), path);
    }

    public record FieldError(String field, String reason) {}
}
