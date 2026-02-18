package com.hotel.service.HotelService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Token validation response with user details")
public class TokenValidationResponse {
    
    @Schema(description = "Token validity status", example = "true")
    @JsonProperty("valid")
    private Boolean valid;
    
    @Schema(description = "User email", example = "admin@example.com")
    @JsonProperty("email")
    private String email;
    
    @Schema(description = "User role", example = "ROLE_ADMIN")
    @JsonProperty("role")
    private String role;
    
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("userId")
    private String userId;
    
    // Custom constructor for Jackson when only valid field is present
    public TokenValidationResponse(Boolean valid) {
        this.valid = valid;
    }
}
