package com.auth.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String name;
    private String about;
    private String email;
    private String role;
    private String userId;
}
