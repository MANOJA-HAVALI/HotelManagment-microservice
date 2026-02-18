package com.auth.service.service;

import com.auth.service.dto.AuthRequest;
import com.auth.service.dto.AuthResponse;
import com.auth.service.dto.RegisterRequest;
import com.auth.service.dto.RegisterResponse;
import com.auth.service.entities.AuthUser;

import java.util.List;

public interface AuthService {

    AuthUser registerUser(RegisterRequest registerRequest);
    AuthUser getUser(String userId);
    List<AuthUser> getAllUsers();
    AuthResponse LoginUser(AuthRequest authRequest);
}
