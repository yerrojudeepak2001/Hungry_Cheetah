package com.foodapp.order.config;

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
                    "/actuator/health",              // Health check
                    "/actuator/info",                // Info endpoint
                    "/v3/api-docs/**",               // Swagger docs
                    "/swagger-ui/**",                // Swagger UI
                    "/swagger-ui.html"               // Swagger UI HTML
                ).permitAll()
                // Order management - Authenticated users only
                .requestMatchers(
                    "POST", "/api/orders/**"         // Create orders - USER, RESTAURANT_OWNER, ADMIN
                ).hasAnyRole("USER", "RESTAURANT_OWNER", "ADMIN")
                .requestMatchers(
                    "GET", "/api/orders/user/**"     // Get user's orders - USER only (own orders)
                ).hasAnyRole("USER", "RESTAURANT_OWNER", "ADMIN")
                .requestMatchers(
                    "GET", "/api/orders/{id}"        // Get specific order - Owner or Admin
                ).hasAnyRole("USER", "RESTAURANT_OWNER", "ADMIN")
                // Order status updates - Restaurant owners and admins
                .requestMatchers(
                    "PUT", "/api/orders/*/status"    // Update order status
                ).hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                // Admin operations
                .requestMatchers(
                    "GET", "/api/orders",             // Get all orders
                    "DELETE", "/api/orders/**"       // Cancel orders
                ).hasRole("ADMIN")
                // All other endpoints require authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}