package com.foodapp.auth.client;

import com.foodapp.auth.dto.UserDetails;
import com.foodapp.auth.dto.UserRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
    @Override
    public UserDetails getUserDetails(String userId) {
        UserDetails userDetails = new UserDetails();
        // Fallback response with basic data
        return userDetails;
    }
    
    @Override
    public UserDetails getUserByEmail(String email) {
        UserDetails userDetails = new UserDetails();
        // Fallback response with basic data
        return userDetails;
    }
    
    @Override
    public UserDetails createUser(UserRegistrationRequest request) {
        UserDetails userDetails = new UserDetails();
        // Fallback response with basic data
        return userDetails;
    }

    @Override
    public void updateLastLogin(String userId) {
        // Do nothing in fallback
    }
}