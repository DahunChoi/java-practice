package com.example.javabasicsapi.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // DTO Validation 에러 (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e, HttpServletRequest req) {
        List<ErrorResponse.FieldError> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldError)
                .toList();

        ErrorResponse body = ErrorResponse.of(
                ErrorCode.VALIDATION_ERROR,
                "Validation Failed",
                errors,
                req.getRequestURI()
        );

        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.status()).body(body);
    }

    // 상품 없음 (404)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ProductNotFoundException e, HttpServletRequest req) {
        ErrorResponse body = ErrorResponse.of(
                ErrorCode.PRODUCT_NOT_FOUND,
                e.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.PRODUCT_NOT_FOUND.status()).body(body);
    }

    // 잘못된 요청 (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException e, HttpServletRequest req) {
        ErrorResponse body = ErrorResponse.of(
                ErrorCode.BAD_REQUEST,
                e.getMessage(),
                req.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.status()).body(body);
    }

    // 그 외 전부 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception e, HttpServletRequest req) {
        ErrorResponse body = ErrorResponse.of(
                ErrorCode.INTERNAL_ERROR,
                "Unexpected error",
                req.getRequestURI()
        );
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.status()).body(body);
    }

    private ErrorResponse.FieldError toFieldError(FieldError fe) {
        String reason = fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid vaalue";
        return new ErrorResponse.FieldError(fe.getField(), reason);
    }
}
