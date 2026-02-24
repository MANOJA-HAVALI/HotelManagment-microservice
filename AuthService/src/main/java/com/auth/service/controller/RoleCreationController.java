package com.auth.service.controller;

import com.auth.service.dto.ApiSuccess;
import com.auth.service.dto.CreateRoleRequest;
import com.auth.service.dto.RoleDto;
import com.auth.service.dto.UpdateRoleRequest;
import com.auth.service.service.RoleCreationService;
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
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "CRUD APIs for role management")
public class RoleCreationController {

    private final RoleCreationService roleCreationService;

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Role created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Role already exists")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleDto roleDto = roleCreationService.createRole(request);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Roles retrieved successfully")
    })
    public ResponseEntity<ApiSuccess<List<RoleDto>>> getAllRoles() {
        List<RoleDto> roleDtos = roleCreationService.getAllRoles();
        return CommonUtils.buildSuccessResponseEntity(roleDtos, "Roles retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by ID", description = "Retrieves the role with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> getRoleById(
            @Parameter(description = "Role ID", required = true) @PathVariable String id) {
        RoleDto roleDto = roleCreationService.getRoleById(id);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role retrieved successfully");
    }

    @GetMapping("/name/{roleName}")
    @Operation(summary = "Get role by name", description = "Retrieves the role with the specified name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> getRoleByName(
            @Parameter(description = "Role name", required = true) @PathVariable String roleName) {
        RoleDto roleDto = roleCreationService.getRoleByName(roleName);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role retrieved successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update role", description = "Updates the role with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Role not found"),
        @ApiResponse(responseCode = "409", description = "Role name already exists")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> updateRole(
            @Parameter(description = "Role ID", required = true) @PathVariable String id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleDto roleDto = roleCreationService.updateRole(id, request);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role", description = "Deletes the role with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiSuccess<Void>> deleteRole(
            @Parameter(description = "Role ID", required = true) @PathVariable String id) {
        roleCreationService.deleteRole(id);
        return CommonUtils.buildSuccessResponseEntity(null, "Role deleted successfully");
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate role", description = "Activates the role with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role activated successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> activateRole(
            @Parameter(description = "Role ID", required = true) @PathVariable String id) {
        RoleDto roleDto = roleCreationService.activateRole(id);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role activated successfully");
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate role", description = "Deactivates the role with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Role deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiSuccess<RoleDto>> deactivateRole(
            @Parameter(description = "Role ID", required = true) @PathVariable String id) {
        RoleDto roleDto = roleCreationService.deactivateRole(id);
        return CommonUtils.buildSuccessResponseEntity(roleDto, "Role deactivated successfully");
    }
}
