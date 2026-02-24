package com.auth.service.controller;

import com.auth.service.dto.ApiSuccess;
import com.auth.service.dto.CreateModuleRequest;
import com.auth.service.dto.ModuleDto;
import com.auth.service.dto.UpdateModuleRequest;
import com.auth.service.service.ModuleService;
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
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Module Management", description = "CRUD APIs for module management")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    @Operation(summary = "Create a new module", description = "Creates a new module with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Module created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Module already exists")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> createModule(@Valid @RequestBody CreateModuleRequest request) {
        ModuleDto moduleDto = moduleService.createModule(request);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module created successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all modules", description = "Retrieves a list of all modules")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modules retrieved successfully")
    })
    public ResponseEntity<ApiSuccess<List<ModuleDto>>> getAllModules() {
        List<ModuleDto> moduleDtos = moduleService.getAllModules();
        return CommonUtils.buildSuccessResponseEntity(moduleDtos, "Modules retrieved successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get module by ID", description = "Retrieves the module with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> getModuleById(
            @Parameter(description = "Module ID", required = true) @PathVariable String id) {
        ModuleDto moduleDto = moduleService.getModuleById(id);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module retrieved successfully");
    }

    @GetMapping("/name/{moduleName}")
    @Operation(summary = "Get module by name", description = "Retrieves the module with the specified name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> getModuleByName(
            @Parameter(description = "Module name", required = true) @PathVariable String moduleName) {
        ModuleDto moduleDto = moduleService.getModuleByName(moduleName);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module retrieved successfully");
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update module", description = "Updates the module with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Module not found"),
        @ApiResponse(responseCode = "409", description = "Module name already exists")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> updateModule(
            @Parameter(description = "Module ID", required = true) @PathVariable String id,
            @Valid @RequestBody UpdateModuleRequest request) {
        ModuleDto moduleDto = moduleService.updateModule(id, request);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete module", description = "Deletes the module with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ApiSuccess<Void>> deleteModule(
            @Parameter(description = "Module ID", required = true) @PathVariable String id) {
        moduleService.deleteModule(id);
        return CommonUtils.buildSuccessResponseEntity(null, "Module deleted successfully");
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate module", description = "Activates the module with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module activated successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> activateModule(
            @Parameter(description = "Module ID", required = true) @PathVariable String id) {
        ModuleDto moduleDto = moduleService.activateModule(id);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module activated successfully");
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate module", description = "Deactivates the module with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found")
    })
    public ResponseEntity<ApiSuccess<ModuleDto>> deactivateModule(
            @Parameter(description = "Module ID", required = true) @PathVariable String id) {
        ModuleDto moduleDto = moduleService.deactivateModule(id);
        return CommonUtils.buildSuccessResponseEntity(moduleDto, "Module deactivated successfully");
    }
}
