package com.auth.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRoleRequest {
    
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    private String roleName;
    
    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;
    
    private Boolean isActive;
}
