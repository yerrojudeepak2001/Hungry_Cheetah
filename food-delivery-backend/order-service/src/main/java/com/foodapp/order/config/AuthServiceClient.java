package com.foodapp.order.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTH-SERVICE", fallback = AuthServiceClientFallback.class)
public interface AuthServiceClient {
    
    @PostMapping("/api/auth/validate-token")
    ApiResponse<TokenValidationResponse> validateToken(@RequestParam("token") String token);
}