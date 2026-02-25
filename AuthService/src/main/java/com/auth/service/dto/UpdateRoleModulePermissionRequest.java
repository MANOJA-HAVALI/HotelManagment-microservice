package com.auth.service.dto;

import lombok.Data;

@Data
public class UpdateRoleModulePermissionRequest {
    
    private Boolean canView;
    
    private Boolean canEdit;
    
    private Boolean canDelete;
    
    private Boolean canDownload;
}
