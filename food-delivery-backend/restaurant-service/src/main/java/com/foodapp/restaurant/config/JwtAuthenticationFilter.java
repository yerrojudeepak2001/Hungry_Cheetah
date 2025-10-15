package com.foodapp.restaurant.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        logger.debug("Processing request: {} {}", request.getMethod(), request.getRequestURI());
        
        String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", authHeader != null ? "Bearer [HIDDEN]" : "null");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                logger.info("Attempting to validate JWT token with auth-service");
                // Validate token with auth-service
                var authResponse = authServiceClient.validateToken(authHeader);
                logger.info("Auth service response - success: {}, data: {}", 
                    authResponse != null ? authResponse.isSuccess() : "null response",
                    authResponse != null && authResponse.getData() != null ? "present" : "null");
                
                if (authResponse != null && authResponse.isSuccess() && authResponse.getData() != null) {
                    TokenValidationResponse userInfo = authResponse.getData();
                    logger.info("Token validation successful for user: {} with roles: {}", 
                        userInfo.getUsername(), userInfo.getRoles());
                    
                    // Create authorities from user roles
                    List<SimpleGrantedAuthority> authorities = userInfo.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());
                    
                    // Set authentication in security context
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(
                                    userInfo.getUsername(), 
                                    null, 
                                    authorities
                            );
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication set in security context");
                } else {
                    logger.warn("Token validation failed - invalid response from auth service");
                }
            } catch (Exception e) {
                // Token validation failed - continue without authentication
                logger.warn("JWT token validation failed: " + e.getMessage(), e);
            }
        } else {
            logger.debug("No valid Authorization header found");
        }
        
        filterChain.doFilter(request, response);
    }
}