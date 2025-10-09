package com.foodapp.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.auth.dto.UserDetails;
import com.foodapp.auth.dto.UserRegistrationRequest;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}")
    UserDetails getUserDetails(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/email/{email}")
    UserDetails getUserByEmail(@PathVariable("email") String email);
    
    @PostMapping("/api/users")
    UserDetails createUser(@RequestBody UserRegistrationRequest request);
    
    @PutMapping("/api/users/{userId}/last-login")
    void updateLastLogin(@PathVariable("userId") String userId);
}