package com.auth.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User data transfer object")
public class UserDto{
    
    @Schema(description = "Unique user identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;
    
    @Schema(description = "User's full name", example = "John Doe")
    private String name;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "User's bio/description", example = "Software developer passionate about microservices")
    private String about;
}
