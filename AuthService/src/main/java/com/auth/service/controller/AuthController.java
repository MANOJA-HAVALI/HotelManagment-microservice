package com.auth.service.controller;

import com.auth.service.dto.AuthRequest;
import com.auth.service.dto.AuthResponse;
import com.auth.service.dto.RegisterRequest;
import com.auth.service.dto.RegisterResponse;
import com.auth.service.dto.TokenValidationResponse;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and Authorization APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

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
        AuthUser registeredUser = authService.registerUser(registerRequest);
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", registeredUser.getRole());
        claims.put("userId", registeredUser.getUserId());
        
        String token = jwtUtil.generateToken(registeredUser.getEmail(), claims);
        
        RegisterResponse response = new RegisterResponse();
        response.setName(registeredUser.getName());
        response.setAbout(registeredUser.getAbout());
        response.setEmail(registeredUser.getEmail());
        response.setRole(registeredUser.getRole());
        response.setUserId(registeredUser.getUserId());
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates user credentials and returns JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<AuthResponse> authenticateUser(
            @Valid @RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        AuthUser user = (AuthUser) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("userId", user.getUserId());

        String token = jwtUtil.generateToken(user.getUsername(), claims);

        AuthResponse response = AuthResponse.builder()
                .token(token)
                .email(user.getUsername())
                .role(user.getRole())
                .userId(user.getUserId())
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
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                if (jwtUtil.validateToken(jwtToken)) {
                    String email = jwtUtil.extractUsername(jwtToken);
                    String role = jwtUtil.extractClaim(jwtToken, claims -> claims.get("role", String.class));
                    String userId = jwtUtil.extractClaim(jwtToken, claims -> claims.get("userId", String.class));
                    
                    TokenValidationResponse response = TokenValidationResponse.builder()
                            .valid(true)
                            .email(email)
                            .role(role)
                            .userId(userId)
                            .build();
                    return ResponseEntity.ok(response);
                }
            } catch (Exception e) {
                // Token is invalid
            }
        }
        
        TokenValidationResponse response = TokenValidationResponse.builder()
                .valid(false)
                .build();
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
        // Don't send password back
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
