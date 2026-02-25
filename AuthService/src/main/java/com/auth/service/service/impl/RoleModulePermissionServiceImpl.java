package com.auth.service.service.impl;

import com.auth.service.dto.CreateRoleModulePermissionRequest;
import com.auth.service.dto.ModuleDto;
import com.auth.service.dto.RoleDto;
import com.auth.service.dto.RoleModulePermissionDto;
import com.auth.service.dto.UpdateRoleModulePermissionRequest;
import com.auth.service.entities.Module;
import com.auth.service.entities.Role;
import com.auth.service.entities.RoleModulePermission;
import com.auth.service.exception.ModuleNotFoundException;
import com.auth.service.exception.RoleModulePermissionAlreadyExistsException;
import com.auth.service.exception.RoleModulePermissionNotFoundException;
import com.auth.service.exception.RoleNotFoundException;
import com.auth.service.repository.ModuleRepository;
import com.auth.service.repository.RoleModulePermissionRepository;
import com.auth.service.repository.RoleRepository;
import com.auth.service.service.RoleModulePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleModulePermissionServiceImpl implements RoleModulePermissionService {

    private final RoleModulePermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;

    @Override
    public RoleModulePermissionDto createPermission(CreateRoleModulePermissionRequest request) {
        // Check if permission already exists for this role and module combination
        if (permissionRepository.findByRoleIdAndModuleId(request.getRoleId(), request.getModuleId()).isPresent()) {
            throw new RoleModulePermissionAlreadyExistsException(
                "Permission already exists for role ID: " + request.getRoleId() + " and module ID: " + request.getModuleId());
        }

        // Validate role exists
        Role role = roleRepository.findById(request.getRoleId())
            .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + request.getRoleId()));

        // Validate module exists
        Module module = moduleRepository.findById(request.getModuleId())
            .orElseThrow(() -> new ModuleNotFoundException("Module not found with ID: " + request.getModuleId()));

        RoleModulePermission permission = RoleModulePermission.builder()
                .id(UUID.randomUUID().toString())
                .role(role)
                .module(module)
                .canView(request.getCanView() != null ? request.getCanView() : false)
                .canEdit(request.getCanEdit() != null ? request.getCanEdit() : false)
                .canDelete(request.getCanDelete() != null ? request.getCanDelete() : false)
                .canDownload(request.getCanDownload() != null ? request.getCanDownload() : false)
                .build();

        RoleModulePermission savedPermission = permissionRepository.save(permission);
        return convertToDto(savedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleModulePermissionDto> getAllPermissions() {
        List<RoleModulePermission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleModulePermissionDto getPermissionById(String id) {
        Optional<RoleModulePermission> permission = permissionRepository.findById(id);
        if (!permission.isPresent()) {
            throw new RoleModulePermissionNotFoundException("Permission not found with ID: " + id);
        }
        return convertToDto(permission.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleModulePermissionDto> getPermissionsByRoleId(String roleId) {
        List<RoleModulePermission> permissions = permissionRepository.findByRoleId(roleId);
        return permissions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleModulePermissionDto> getPermissionsByModuleId(String moduleId) {
        List<RoleModulePermission> permissions = permissionRepository.findByModuleId(moduleId);
        return permissions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleModulePermissionDto getPermissionByRoleAndModule(String roleId, String moduleId) {
        Optional<RoleModulePermission> permission = permissionRepository.findByRoleIdAndModuleId(roleId, moduleId);
        if (!permission.isPresent()) {
            throw new RoleModulePermissionNotFoundException(
                "Permission not found for role ID: " + roleId + " and module ID: " + moduleId);
        }
        return convertToDto(permission.get());
    }

    @Override
    public RoleModulePermissionDto updatePermission(String id, UpdateRoleModulePermissionRequest request) {
        Optional<RoleModulePermission> existingPermissionOpt = permissionRepository.findById(id);
        if (!existingPermissionOpt.isPresent()) {
            throw new RoleModulePermissionNotFoundException("Permission not found with ID: " + id);
        }

        RoleModulePermission permission = existingPermissionOpt.get();

        if (request.getCanView() != null) {
            permission.setCanView(request.getCanView());
        }
        if (request.getCanEdit() != null) {
            permission.setCanEdit(request.getCanEdit());
        }
        if (request.getCanDelete() != null) {
            permission.setCanDelete(request.getCanDelete());
        }
        if (request.getCanDownload() != null) {
            permission.setCanDownload(request.getCanDownload());
        }

        RoleModulePermission updatedPermission = permissionRepository.save(permission);
        return convertToDto(updatedPermission);
    }

    @Override
    public void deletePermission(String id) {
        if (!permissionRepository.existsById(id)) {
            throw new RoleModulePermissionNotFoundException("Permission not found with ID: " + id);
        }
        permissionRepository.deleteById(id);
    }

    @Override
    public void deletePermissionByRoleAndModule(String roleId, String moduleId) {
        Optional<RoleModulePermission> permission = permissionRepository.findByRoleIdAndModuleId(roleId, moduleId);
        if (!permission.isPresent()) {
            throw new RoleModulePermissionNotFoundException(
                "Permission not found for role ID: " + roleId + " and module ID: " + moduleId);
        }
        permissionRepository.deleteByRoleIdAndModuleId(roleId, moduleId);
    }

    private RoleModulePermissionDto convertToDto(RoleModulePermission permission) {
        RoleModulePermissionDto dto = new RoleModulePermissionDto();
        dto.setId(permission.getId());
        
        // Convert role to DTO
        if (permission.getRole() != null) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(permission.getRole().getId());
            roleDto.setRoleName(permission.getRole().getRoleName());
            roleDto.setDescription(permission.getRole().getDescription());
            roleDto.setIsActive(permission.getRole().getIsActive());
            dto.setRole(roleDto);
        }
        
        // Convert module to DTO
        if (permission.getModule() != null) {
            ModuleDto moduleDto = new ModuleDto();
            moduleDto.setId(permission.getModule().getId());
            moduleDto.setModuleName(permission.getModule().getModuleName());
            moduleDto.setDescription(permission.getModule().getDescription());
            moduleDto.setIsActive(permission.getModule().getIsActive());
            dto.setModule(moduleDto);
        }
        
        dto.setCanView(permission.getCanView());
        dto.setCanEdit(permission.getCanEdit());
        dto.setCanDelete(permission.getCanDelete());
        dto.setCanDownload(permission.getCanDownload());
        
        return dto;
    }
}
