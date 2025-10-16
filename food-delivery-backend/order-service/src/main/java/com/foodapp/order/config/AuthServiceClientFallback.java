package com.foodapp.order.config;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    
    @Override
    public ApiResponse<TokenValidationResponse> validateToken(String token) {
        // Return invalid token response when auth service is down
        TokenValidationResponse invalidResponse = new TokenValidationResponse();
        invalidResponse.setValid(false);
        invalidResponse.setRoles(Collections.emptySet());
        return new ApiResponse<>(false, "Auth service unavailable", invalidResponse);
    }
}