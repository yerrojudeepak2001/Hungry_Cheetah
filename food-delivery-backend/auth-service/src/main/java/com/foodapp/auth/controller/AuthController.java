package com.foodapp.auth.controller;

import com.foodapp.auth.service.AuthService;
import com.foodapp.auth.dto.RegisterRequest;
import com.foodapp.auth.dto.LoginRequest;
import com.foodapp.auth.dto.RefreshTokenRequest;
import com.foodapp.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", token));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", token));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newToken = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new ApiResponse<>(true, "Token refreshed", newToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Logged out successfully", null));
    }
}