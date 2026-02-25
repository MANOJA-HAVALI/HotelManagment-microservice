package com.auth.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRoleModulePermissionRequest {
    
    @NotBlank(message = "Role ID is required")
    private String roleId;
    
    @NotBlank(message = "Module ID is required")
    private String moduleId;
    
    @NotNull(message = "Can view permission is required")
    private Boolean canView = false;
    
    @NotNull(message = "Can edit permission is required")
    private Boolean canEdit = false;
    
    @NotNull(message = "Can delete permission is required")
    private Boolean canDelete = false;
    
    @NotNull(message = "Can download permission is required")
    private Boolean canDownload = false;
}
