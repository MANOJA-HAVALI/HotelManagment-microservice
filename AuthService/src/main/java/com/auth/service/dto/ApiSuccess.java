package com.auth.service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiSuccess<T> {
    
    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private T data;
}
