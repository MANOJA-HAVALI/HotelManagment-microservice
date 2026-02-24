package com.auth.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ModuleAlreadyExistsException extends RuntimeException {
    public ModuleAlreadyExistsException(String message) {
        super(message);
    }
}
