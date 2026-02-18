package com.hotel.service.HotelService.util;

import com.hotel.service.HotelService.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            logger.debug("Current authenticated user: {}, authorities: {}", 
                authentication.getName(), 
                authentication.getAuthorities());
            return authentication.getName();
        }
        logger.warn("No authenticated user found in security context");
        throw new ForbiddenException("User not authenticated");
    }

    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean hasRole = authorities.stream()
                    .anyMatch(authority -> authority.getAuthority().equals(role));
            logger.debug("User {} has role {}: {}", authentication.getName(), role, hasRole);
            return hasRole;
        }
        logger.warn("No authenticated user found when checking role: {}", role);
        return false;
    }

    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            boolean hasAnyRole = authorities.stream()
                    .anyMatch(authority -> {
                        String authorityStr = authority.getAuthority();
                        for (String role : roles) {
                            if (authorityStr.equals(role)) {
                                return true;
                            }
                        }
                        return false;
                    });
            logger.debug("User {} has any of roles {}: {}", 
                authentication.getName(), 
                String.join(", ", roles), 
                hasAnyRole);
            return hasAnyRole;
        }
        logger.warn("No authenticated user found when checking roles: {}", String.join(", ", roles));
        return false;
    }

    public static void checkRole(String role) {
        logger.debug("Checking if user has role: {}", role);
        if (!hasRole(role)) {
            logger.error("Access denied for user {}. Required role: {}", 
                getCurrentUserEmail(), role);
            throw new ForbiddenException("Access denied. Required role: " + role);
        }
        logger.debug("User has required role: {}", role);
    }

    public static void checkAnyRole(String... roles) {
        logger.debug("Checking if user has any of roles: {}", String.join(", ", roles));
        if (!hasAnyRole(roles)) {
            logger.error("Access denied for user {}. Required one of roles: {}", 
                getCurrentUserEmail(), 
                String.join(", ", roles));
            throw new ForbiddenException("Access denied. Required one of roles: " + String.join(", ", roles));
        }
        logger.debug("User has required roles");
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
