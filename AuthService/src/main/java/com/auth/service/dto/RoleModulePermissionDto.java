package com.auth.service.dto;

import lombok.Data;

@Data
public class RoleModulePermissionDto {
    private String id;
    private RoleDto role;
    private ModuleDto module;
    private Boolean canView;
    private Boolean canEdit;
    private Boolean canDelete;
    private Boolean canDownload;
}
