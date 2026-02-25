package com.auth.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RoleModulePermissionAlreadyExistsException extends RuntimeException {
    public RoleModulePermissionAlreadyExistsException(String message) {
        super(message);
    }
}
