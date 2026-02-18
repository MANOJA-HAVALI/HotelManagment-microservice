package com.auth.service.dto;

import lombok.Data;

@Data
public class RegisterResponse {
    private String name;
    private String about;
    private String email;
    private String role;
    private String userId;
}
