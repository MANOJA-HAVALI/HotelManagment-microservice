package com.hotel.service.HotelService.security;

import com.hotel.service.HotelService.dto.TokenValidationResponse;
import com.hotel.service.HotelService.extrenal.AuthServiceClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthServiceTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceTokenFilter.class);

    @Autowired
    private AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            try {
                logger.debug("Validating token with AuthService");
                // Validate token with AuthService
                TokenValidationResponse tokenResponse = authServiceClient.validateToken(requestTokenHeader);
                
                if (tokenResponse != null && Boolean.TRUE.equals(tokenResponse.getValid())) {
                    logger.debug("Token validation successful for user: {}", tokenResponse.getEmail());
                    // Create authentication token with actual user role
                    String role = tokenResponse.getRole();
                    if (role == null) {
                        role = "ROLE_USER"; // Default role if not provided
                    }
                    
                    List<SimpleGrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(role)
                    );
                    
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            tokenResponse.getEmail(), null, authorities);
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.warn("Token validation failed: Invalid token");
                }
            } catch (Exception e) {
                logger.error("Token validation failed: {}", e.getMessage(), e);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
