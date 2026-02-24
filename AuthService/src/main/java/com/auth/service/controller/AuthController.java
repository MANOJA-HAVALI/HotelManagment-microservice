package com.auth.service.controller;

import com.auth.service.dto.*;
import com.auth.service.entities.AuthUser;
import com.auth.service.security.JwtUtil;
import com.auth.service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and Authorization APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns authentication token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = authService.registerUser(registerRequest);
        return ResponseEntity.ok(registerResponse);
    }


    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates user credentials and returns JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ApiSuccess<AuthResponse>> login (@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.loginUser(authRequest);

        ApiSuccess response = ApiSuccess.builder()
                .timestamp(java.time.Instant.now().toString())
                .success(true)
                .message("Login successful")
                .data(authResponse)
                .build();
                
        return ResponseEntity.ok(response);
    }


    @PostMapping("/validate")
    @Operation(summary = "Validate JWT token", description = "Validates the provided JWT token and returns user details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token validation result with user details"),
        @ApiResponse(responseCode = "400", description = "Invalid token format")
    })
    public ResponseEntity<TokenValidationResponse> validateToken(@Parameter(description = "Bearer token", required = true) @RequestHeader("Authorization") String token) {
        TokenValidationResponse response = authService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get current user profile", description = "Retrieves the profile of the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<AuthUser> getCurrentUser(Authentication authentication) {
        AuthUser user = (AuthUser) authentication.getPrincipal();
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by ID", description = "Retrieves the user with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<AuthUser> getUser(@PathVariable String userId) {
        AuthUser user = authService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping("/users")
    public ResponseEntity<List<AuthUser>> getAllUsers() {
        List<AuthUser> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
