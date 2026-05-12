package com.foodapp.restaurant.config;

import org.springframework.stereotype.Component;
import com.foodapp.restaurant.dto.ApiResponse;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {

    @Override
    public ApiResponse<TokenValidationResponse> validateToken(String token) {
        // Return failure response when auth service is down
        return new ApiResponse<>(false, "Auth service unavailable", null);
    }
}