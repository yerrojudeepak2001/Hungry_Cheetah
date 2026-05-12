package com.foodapp.admin.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.foodapp.admin.dto.AuthValidationResponse;

@Component
public class AuthClientFallback implements AuthClient {
    
    @Override
    public ResponseEntity<AuthValidationResponse> validateToken(String token) {
        // Return invalid response when Auth service is down
        AuthValidationResponse response = new AuthValidationResponse();
        response.setValid(false);
        response.setMessage("Auth service unavailable");
        
        return ResponseEntity.ok(response);
    }
}