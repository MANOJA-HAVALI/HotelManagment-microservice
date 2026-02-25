package com.auth.service.controller;

import com.auth.service.dto.ApiSuccess;
import com.auth.service.dto.CreateRoleModulePermissionRequest;
import com.auth.service.dto.RoleModulePermissionDto;
import com.auth.service.dto.UpdateRoleModulePermissionRequest;
import com.auth.service.service.RoleModulePermissionService;
import com.auth.service.util.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-module-permissions")
@RequiredArgsConstructor
@Tag(name = "Role Module Permission Management", description = "CRUD APIs for role-module permission management")
public class RoleModulePermissionController {

    private final RoleModulePermissionService permissionService;

    @PostMapping
    @Operation(summary = "Create a new role-module permission", description = "Creates a new permission for a role on a specific module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Permission created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Role or Module not found"),
        @ApiResponse(responseCode = "409", description = "Permission already exists for this role and module")
    })
    public ResponseEntity<ApiSuccess<RoleModulePermissionDto>> createPermission(@Valid @RequestBody CreateRoleModulePermissionRequest request) {
        RoleModulePermissionDto permissionDto = permissionService.createPermission(request);
        return CommonUtils.buildSuccessResponseEntity(permissionDto, "Permission created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all permissions", description = "Retrieves a list of all role-module permissions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    })
    public ResponseEntity<ApiSuccess<List<RoleModulePermissionDto>>> getAllPermissions() {
        List<RoleModulePermissionDto> permissionDtos = permissionService.getAllPermissions();
        return CommonUtils.buildSuccessResponseEntity(permissionDtos, "Permissions retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get permission by ID", description = "Retrieves the permission with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permission retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<ApiSuccess<RoleModulePermissionDto>> getPermissionById(
            @Parameter(description = "Permission ID", required = true) @PathVariable String id) {
        RoleModulePermissionDto permissionDto = permissionService.getPermissionById(id);
        return CommonUtils.buildSuccessResponseEntity(permissionDto, "Permission retrieved successfully");
    }

    @GetMapping("/role/{roleId}")
    @Operation(summary = "Get permissions by role", description = "Retrieves all permissions for a specific role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    })
    public ResponseEntity<ApiSuccess<List<RoleModulePermissionDto>>> getPermissionsByRole(
            @Parameter(description = "Role ID", required = true) @PathVariable String roleId) {
        List<RoleModulePermissionDto> permissionDtos = permissionService.getPermissionsByRoleId(roleId);
        return CommonUtils.buildSuccessResponseEntity(permissionDtos, "Permissions retrieved successfully");
    }

    @GetMapping("/module/{moduleId}")
    @Operation(summary = "Get permissions by module", description = "Retrieves all permissions for a specific module")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permissions retrieved successfully")
    })
    public ResponseEntity<ApiSuccess<List<RoleModulePermissionDto>>> getPermissionsByModule(
            @Parameter(description = "Module ID", required = true) @PathVariable String moduleId) {
        List<RoleModulePermissionDto> permissionDtos = permissionService.getPermissionsByModuleId(moduleId);
        return CommonUtils.buildSuccessResponseEntity(permissionDtos, "Permissions retrieved successfully");
    }

    @GetMapping("/role/{roleId}/module/{moduleId}")
    @Operation(summary = "Get permission by role and module", description = "Retrieves the permission for a specific role and module combination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permission retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<ApiSuccess<RoleModulePermissionDto>> getPermissionByRoleAndModule(
            @Parameter(description = "Role ID", required = true) @PathVariable String roleId,
            @Parameter(description = "Module ID", required = true) @PathVariable String moduleId) {
        RoleModulePermissionDto permissionDto = permissionService.getPermissionByRoleAndModule(roleId, moduleId);
        return CommonUtils.buildSuccessResponseEntity(permissionDto, "Permission retrieved successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update permission", description = "Updates the permission with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<ApiSuccess<RoleModulePermissionDto>> updatePermission(
            @Parameter(description = "Permission ID", required = true) @PathVariable String id,
            @Valid @RequestBody UpdateRoleModulePermissionRequest request) {
        RoleModulePermissionDto permissionDto = permissionService.updatePermission(id, request);
        return CommonUtils.buildSuccessResponseEntity(permissionDto, "Permission updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete permission", description = "Deletes the permission with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<ApiSuccess<Void>> deletePermission(
            @Parameter(description = "Permission ID", required = true) @PathVariable String id) {
        permissionService.deletePermission(id);
        return CommonUtils.buildSuccessResponseEntity(null, "Permission deleted successfully");
    }

    @DeleteMapping("/role/{roleId}/module/{moduleId}")
    @Operation(summary = "Delete permission by role and module", description = "Deletes the permission for a specific role and module combination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permission deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Permission not found")
    })
    public ResponseEntity<ApiSuccess<Void>> deletePermissionByRoleAndModule(
            @Parameter(description = "Role ID", required = true) @PathVariable String roleId,
            @Parameter(description = "Module ID", required = true) @PathVariable String moduleId) {
        permissionService.deletePermissionByRoleAndModule(roleId, moduleId);
        return CommonUtils.buildSuccessResponseEntity(null, "Permission deleted successfully");
    }
}
