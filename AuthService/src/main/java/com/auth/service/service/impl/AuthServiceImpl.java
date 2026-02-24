package com.auth.service.service.impl;

import com.auth.service.dto.*;
import com.auth.service.entities.AuthUser;
import com.auth.service.entities.Hotel;
import com.auth.service.entities.Rating;
import com.auth.service.exception.ResourceNotFoundException;
import com.auth.service.extrenal.HotelServiceClient;
import com.auth.service.extrenal.RatingServiceClient;
import com.auth.service.repository.AuthUserRepository;
import com.auth.service.security.JwtUtil;
import com.auth.service.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelServiceClient hotelServiceClient;
    private final RatingServiceClient ratingServiceClient;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, HotelServiceClient hotelService, RatingServiceClient ratingServiceClient, RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.hotelServiceClient = hotelService;
        this.ratingServiceClient = ratingServiceClient;
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public RegisterResponse  registerUser(RegisterRequest registerRequest) {
        // Check if user already exists
        if (authUserRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + registerRequest.getEmail());
        }

        // Create AuthUser for authentication
        AuthUser authUser = new AuthUser();
        String userId = UUID.randomUUID().toString();
        authUser.setUserId(userId);
        authUser.setName(registerRequest.getName());
        authUser.setEmail(registerRequest.getEmail());
        authUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //authUser.setRole(registerRequest.getRole());
        authUser.setAbout(registerRequest.getAbout());

        // Save AuthUser
        AuthUser savedAuthUser = authUserRepository.save(authUser);

        // Map entity to response DTO
        return RegisterResponse.builder()
                .userId(savedAuthUser.getUserId())
                .name(savedAuthUser.getName())
                .email(savedAuthUser.getEmail())
                //.role(savedAuthUser.getRole())
                .about(savedAuthUser.getAbout())
                .build();

    }

    @Override
    public AuthResponse loginUser(AuthRequest authRequest) {

        AuthUser authUser = authUserRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authRequest.getEmail()));

        // 3. Generate JWT
        String token = jwtUtil.generateToken(authUser.getEmail());

        // 4. Return response
        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .email(authUser.getEmail())
                //.role(authUser.getRole())
                .userId(authUser.getUserId())
                .build();

    }

    @Override
    public AuthUser getUser(String userId) {
        AuthUser AuthUser = authUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        //http://localhost:8082/api/ratings/users/b0066374-e7d1-4d52-9988-a76f428bae28
        try {
            List<Rating> ratingForUser = ratingServiceClient.getRatingsByUserId(AuthUser.getUserId());
            log.info("{}", ratingForUser);

            if (ratingForUser != null) {
                List<Rating> ratings = ratingForUser;

                List<Rating> ratingList = ratings.stream().map(rating -> {
                    //==============================using feign client ======================================================
                    Hotel hotel = hotelServiceClient.getHotel(rating.getHotelId());
                    rating.setHotel(hotel);
                    return rating;
                }).collect(Collectors.toList());

                AuthUser.setRatings(ratingList);
            }
        } catch (Exception e) {
            log.warn("Failed to fetch ratings for user {}: {}", AuthUser.getUserId(), e.getMessage());
            // Continue without ratings if service is unavailable
        }
        return AuthUser;
    }

    @Override
    public List<AuthUser> getAllUsers() {
        return authUserRepository.findAll();
    }

    @Override
    public TokenValidationResponse validateToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                if (jwtUtil.validateToken(jwtToken)) {
                    String email = jwtUtil.extractUsername(jwtToken);
                    String role = jwtUtil.extractClaim(jwtToken, claims -> claims.get("role", String.class));
                    String userId = jwtUtil.extractClaim(jwtToken, claims -> claims.get("userId", String.class));

                    return TokenValidationResponse.builder()
                            .valid(true)
                            .email(email)
                            .role(role)
                            .userId(userId)
                            .build();
                }
            } catch (Exception e) {
                // Token is invalid
            }
        }

        return TokenValidationResponse.builder()
                .valid(false)
                .build();
    }

}
