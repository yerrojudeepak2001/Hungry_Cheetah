package com.foodapp.order.config;

import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthServiceClientFallback implements AuthServiceClient {
    
    @Override
    public UserAuthInfo validateToken(String token) {
        // Return invalid user info when auth service is down
        return new UserAuthInfo(null, null, Collections.emptyList(), false, null);
    }
}