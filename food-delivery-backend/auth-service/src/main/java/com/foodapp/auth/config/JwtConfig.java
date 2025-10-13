package com.foodapp.auth.config;

import com.foodapp.common.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        JwtTokenProvider provider = new JwtTokenProvider();
        // We need to manually set these properties since we're creating the bean manually
        try {
            java.lang.reflect.Field secretField = JwtTokenProvider.class.getDeclaredField("jwtSecret");
            secretField.setAccessible(true);
            secretField.set(provider, jwtSecret);
            
            java.lang.reflect.Field expirationField = JwtTokenProvider.class.getDeclaredField("jwtExpiration");
            expirationField.setAccessible(true);
            expirationField.set(provider, jwtExpiration);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize JwtTokenProvider", e);
        }
        return provider;
    }
}