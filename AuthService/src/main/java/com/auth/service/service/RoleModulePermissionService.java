package com.auth.service.service;

import com.auth.service.dto.CreateRoleModulePermissionRequest;
import com.auth.service.dto.RoleModulePermissionDto;
import com.auth.service.dto.UpdateRoleModulePermissionRequest;

import java.util.List;

public interface RoleModulePermissionService {
    
    RoleModulePermissionDto createPermission(CreateRoleModulePermissionRequest request);
    
    List<RoleModulePermissionDto> getAllPermissions();
    
    RoleModulePermissionDto getPermissionById(String id);
    
    List<RoleModulePermissionDto> getPermissionsByRoleId(String roleId);
    
    List<RoleModulePermissionDto> getPermissionsByModuleId(String moduleId);
    
    RoleModulePermissionDto getPermissionByRoleAndModule(String roleId, String moduleId);
    
    RoleModulePermissionDto updatePermission(String id, UpdateRoleModulePermissionRequest request);
    
    void deletePermission(String id);
    
    void deletePermissionByRoleAndModule(String roleId, String moduleId);
}
