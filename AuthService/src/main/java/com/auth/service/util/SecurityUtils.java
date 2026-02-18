package com.auth.service.util;

import com.auth.service.exception.ForbiddenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        throw new ForbiddenException("User not authenticated");
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream()
                    .anyMatch(authority -> authority.getAuthority().equals(role));
        }
        return false;
    }

    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            return authorities.stream()
                    .anyMatch(authority -> {
                        String authorityStr = authority.getAuthority();
                        for (String role : roles) {
                            if (authorityStr.equals(role)) {
                                return true;
                            }
                        }
                        return false;
                    });
        }
        return false;
    }

    public static void checkRole(String role) {
        if (!hasRole(role)) {
            throw new ForbiddenException("Access denied. Required role: " + role);
        }
    }

    public static void checkAnyRole(String... roles) {
        if (!hasAnyRole(roles)) {
            throw new ForbiddenException("Access denied. Required one of roles: " + String.join(", ", roles));
        }
    }

    public static void checkResourceOwnership(String resourceUserId) {
        String currentUserEmail = getCurrentUserEmail();
        // You might need to fetch user details to compare userId
        // This is a basic implementation - you may need to enhance it
        if (!currentUserEmail.equals(resourceUserId)) {
            throw new ForbiddenException("Access denied. You can only access your own resources");
        }
    }
}
