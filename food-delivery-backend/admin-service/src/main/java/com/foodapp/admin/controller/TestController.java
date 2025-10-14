package com.foodapp.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/v1/admin")
public class TestController {

    @GetMapping("/test/auth")
    public Object testAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return "Authenticated as: " + auth.getName() + 
                   ", Authorities: " + auth.getAuthorities() + 
                   ", Is Authenticated: " + auth.isAuthenticated();
        } else {
            return "No authentication found";
        }
    }
    
    @GetMapping("/test/public")
    public String testPublic() {
        return "Public endpoint working - no authentication required";
    }
}