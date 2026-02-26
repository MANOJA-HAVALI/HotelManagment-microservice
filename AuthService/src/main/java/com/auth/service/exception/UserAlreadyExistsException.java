package com.auth.service.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException (String msg) {
        super(msg);
    }
}
