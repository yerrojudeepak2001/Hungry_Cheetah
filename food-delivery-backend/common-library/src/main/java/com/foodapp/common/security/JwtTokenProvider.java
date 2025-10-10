package com.foodapp.common.security;

<<<<<<< HEAD
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
=======
import com.foodapp.common.constants.AppConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
>>>>>>> version1.4
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
<<<<<<< HEAD
import java.nio.charset.StandardCharsets;
import java.time.Instant;
=======
>>>>>>> version1.4
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;
    
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public String generateToken(String username, Map<String, Object> claims) {
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
<<<<<<< HEAD
                .claims(claims)  // use claims() instead of setClaims()
                .subject(subject) // use subject() instead of setSubject()
                .issuedAt(now)   // use issuedAt() instead of setIssuedAt()
                .expiration(expiryDate) // use expiration() instead of setExpiration()
                .signWith(getSigningKey())
=======
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
>>>>>>> version1.4
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
<<<<<<< HEAD
        return Jwts.parser()  // use parser() directly
                .verifyWith(getSigningKey()) // use verifyWith() instead of setSigningKey()
=======
        return Jwts.parser()
                .verifyWith(getSecretKey())
>>>>>>> version1.4
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
<<<<<<< HEAD
            Jwts.parser() // use parser() directly
                .verifyWith(getSigningKey()) // use verifyWith() instead of setSigningKey()
=======
            Jwts.parser()
                .verifyWith(getSecretKey())
>>>>>>> version1.4
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String refreshToken(String token) {
<<<<<<< HEAD
        final Date now = new Date();
        final Date expiryDate = calculateExpirationDate(now);

        Claims oldClaims = getAllClaimsFromToken(token);
        Map<String, Object> claims = new HashMap<>(oldClaims);
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
=======
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        
        return Jwts.builder()
                .claims(claims)
                .issuedAt(createdDate)
                .expiration(expirationDate)
                .signWith(getSecretKey())
>>>>>>> version1.4
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtExpiration);
    }
}