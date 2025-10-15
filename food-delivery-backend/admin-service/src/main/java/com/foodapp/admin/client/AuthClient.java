package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.foodapp.admin.dto.AuthValidationResponse;

@FeignClient(
    name = "auth-service",
    url = "${services.auth-service.url:http://localhost:8080}",
    fallback = AuthClientFallback.class
)
public interface AuthClient {
    
    @GetMapping("/api/v1/auth/validate")
    ResponseEntity<AuthValidationResponse> validateToken(@RequestHeader("Authorization") String token);
}