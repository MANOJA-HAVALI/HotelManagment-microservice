package com.auth.service.service;

import com.auth.service.dto.*;
import com.auth.service.entities.AuthUser;

import java.util.List;

public interface AuthService {

    RegisterResponse registerUser(RegisterRequest registerRequest);
    AuthUser getUser(String userId);
    List<AuthUser> getAllUsers();
    AuthResponse loginUser(AuthRequest authRequest);
    TokenValidationResponse validateToken(String token);
}
