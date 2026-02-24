package com.auth.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateModuleRequest {
    
    @NotBlank(message = "Module name is required")
    @Size(min = 2, max = 50, message = "Module name must be between 2 and 50 characters")
    private String moduleName;
    
    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;
    
    private Boolean isActive = true;
}
