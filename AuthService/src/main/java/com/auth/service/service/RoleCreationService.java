package com.auth.service.service;

import com.auth.service.dto.CreateRoleRequest;
import com.auth.service.dto.RoleDto;
import com.auth.service.dto.UpdateRoleRequest;

import java.util.List;

public interface RoleCreationService {
    RoleDto createRole(CreateRoleRequest request);
    List<RoleDto> getAllRoles();
    RoleDto getRoleById(String id);
    RoleDto getRoleByName(String roleName);
    RoleDto updateRole(String id, UpdateRoleRequest request);
    void deleteRole(String id);
    RoleDto activateRole(String id);
    RoleDto deactivateRole(String id);
}
