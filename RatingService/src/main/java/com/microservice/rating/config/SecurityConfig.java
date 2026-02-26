package com.microservice.rating.config;

import com.microservice.rating.security.AuthServiceTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthServiceTokenFilter authServiceTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - only allow creating ratings
                        .requestMatchers("POST", "/ratings").permitAll()
                        .requestMatchers(request -> 
                            request.getHeader("X-Internal-Service-Call") != null &&
                            request.getHeader("X-Internal-Service-Call").equals("true")
                        ).permitAll()  // Allow internal service calls
                        // Health check and documentation endpoints
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html","/eureka/**","/actuator/**").permitAll()
                        // Protected endpoints - require authentication for all other rating operations
                        .requestMatchers("/ratings/**").hasRole("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authServiceTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
