package com.foodapp.restaurant.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.foodapp.common.dto.ApiResponse;

@FeignClient(name = "AUTH-SERVICE", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
    
    @GetMapping("/api/auth/userinfo")
    ApiResponse<TokenValidationResponse> validateToken(@RequestHeader("Authorization") String token);
}