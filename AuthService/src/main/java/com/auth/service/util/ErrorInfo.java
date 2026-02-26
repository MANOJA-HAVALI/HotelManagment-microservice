package com.auth.service.util;

public enum ErrorInfo {

    USER_NOT_FOUND(100, "User not found"),
    USER_ALREADY_EXISTS(101, "User already exists"),
    INVALID_TOKEN(103, "Invalid or expired token"),
    ACCESS_DENIED(104, "Access denied"),
    ROLE_NOT_FOUND(105, "Role not found"),
    VALIDATION_FAILED(105, "Validation failed");

    private final int code;
    private final String message;

    ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
