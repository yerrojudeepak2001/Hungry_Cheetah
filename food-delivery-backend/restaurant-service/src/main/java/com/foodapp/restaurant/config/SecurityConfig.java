package com.foodapp.restaurant.config;

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

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints - no authentication required
                .requestMatchers(
                    "/api/restaurants/search",       // Public restaurant search
                    "/api/restaurants/{id}",         // Public restaurant details
                    "/api/restaurants/{id}/menu",    // Public menu viewing
                    "/api/restaurants/{id}/reviews", // Public reviews
                    "/actuator/health",              // Health check
                    "/actuator/info",                // Info endpoint
                    "/v3/api-docs/**",               // Swagger docs
                    "/swagger-ui/**",                // Swagger UI
                    "/swagger-ui.html"               // Swagger UI HTML
                ).permitAll()
                // Restaurant management - Admin only
                .requestMatchers(
                    "POST", "/api/restaurants"       // Register restaurant
                ).hasRole("ADMIN")
                // Restaurant operations - Owner or Admin
                .requestMatchers(
                    "PUT", "/api/restaurants/{id}",  // Update restaurant
                    "DELETE", "/api/restaurants/{id}" // Delete restaurant
                ).hasAnyRole("ADMIN", "RESTAURANT_OWNER")
                // Menu management - Owner or Admin
                .requestMatchers(
                    "/api/restaurants/{id}/menu/**" // Menu management
                ).hasAnyRole("ADMIN", "RESTAURANT_OWNER")
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}