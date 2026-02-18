package com.microservice.rating.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Token validation response with user details")
public class TokenValidationResponse {
    
    @Schema(description = "Token validity status", example = "true")
    private boolean valid;
    
    @Schema(description = "User email", example = "admin@example.com")
    private String email;
    
    @Schema(description = "User role", example = "ROLE_ADMIN")
    private String role;
    
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;
}
