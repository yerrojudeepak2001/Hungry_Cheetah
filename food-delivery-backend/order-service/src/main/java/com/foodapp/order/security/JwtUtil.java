package com.foodapp.order.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    @Value("${jwt.secret:34510220d4bf1609c88acb0256b07a2e4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b}")
    private String jwtSecret;

    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public String getUserIdFromHeaders(String authToken, String userIdHeader) {
        // Priority: X-User-Id header from gateway, then extract from token
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
            return userIdHeader;
        }

        if (authToken != null && validateToken(authToken)) {
            return getUsernameFromToken(authToken);
        }

        return null;
    }
}