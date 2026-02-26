package com.auth.service.util;

import com.auth.service.dto.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;

public class CommonUtils {

    public static <T> ResponseEntity<ApiSuccess<T>> buildResponseEntity(ApiSuccess<T> apiSuccess) {
        return ResponseEntity.ok(apiSuccess);
    }

    public static <T> ResponseEntity<ApiSuccess<T>> buildResponseEntity(ApiSuccess<T> apiSuccess, HttpStatus status) {
        return ResponseEntity.status(status).body(apiSuccess);
    }

    public static <T> ApiSuccess<T> buildSuccessResponse(T data, String message) {
        return ApiSuccess.<T>builder()
                .timestamp(LocalDateTime.now())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiSuccess<T> buildSuccessResponse(T data, String message, HttpStatus status) {
        return ApiSuccess.<T>builder()
                .timestamp(LocalDateTime.parse(Instant.now().toString()))
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiSuccess<T> buildErrorResponse(String message) {
        return ApiSuccess.<T>builder()
                .timestamp(LocalDateTime.parse(Instant.now().toString()))
                .success(false)
                .message(message)
                .data(null)
                .build();
    }

    public static <T> ResponseEntity<ApiSuccess<T>> buildSuccessResponseEntity(T data, String message) {
        return ResponseEntity.ok(buildSuccessResponse(data, message));
    }

    public static <T> ResponseEntity<ApiSuccess<T>> buildSuccessResponseEntity(T data, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(buildSuccessResponse(data, message));
    }

    public static <T> ResponseEntity<ApiSuccess<T>> buildErrorResponseEntity(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(buildErrorResponse(message));
    }
}
