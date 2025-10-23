package com.foodapp.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.foodapp.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
//@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
public class TestController {

    @GetMapping("/connection")
    public ResponseEntity<ApiResponse<Map<String, Object>>> testConnection() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "connected");
        response.put("message", "Backend is successfully connected to frontend");
        response.put("timestamp", System.currentTimeMillis());
        response.put("service", "auth-service");
        response.put("port", 8082);
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Connection successful", response));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "AUTH-SERVICE");
        health.put("version", "1.0.0");
        
        return ResponseEntity.ok(new ApiResponse<>(true, "Service is healthy", health));
    }
}