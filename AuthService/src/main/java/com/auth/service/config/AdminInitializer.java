package com.auth.service.config;

import com.auth.service.entities.AuthUser;
import com.auth.service.entities.Role;
import com.auth.service.repository.AuthUserRepository;
import com.auth.service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        // Only create admin user if it doesn't exist
        if (!authUserRepository.existsByEmail("admin@hotel.com")) {

            // Create role only if it doesn't exist
            Role superAdminRole = roleRepository.findByRoleName("SUPER_ADMIN")
                    .orElseGet(() -> {
                        // Create new role if not found
                        Role newRole = Role.builder()
                                .roleName("SUPER_ADMIN")
                                .description("System Super Admin")
                                .isActive(true)
                                .build();
                        return roleRepository.save(newRole);
                    });

            // Create and save admin user with role
            AuthUser admin = new AuthUser();
            admin.setName("Super Admin");
            admin.setEmail("admin@hotel.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAbout("I am admin of this company");
            admin.setRole(superAdminRole);

            authUserRepository.save(admin);
            System.out.println("Super admin user created successfully");

        } else {
            System.out.println("Admin user already exists");
        }

    }
}
