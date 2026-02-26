package com.auth.service.exception;

public class UserNotFoundException extends RuntimeException {

    private final Integer errorCode;

    public UserNotFoundException(int errorCode, String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "UserNotFoundException{errorCode='" + errorCode + "', message='" + getMessage() + "'}";
    }
}
