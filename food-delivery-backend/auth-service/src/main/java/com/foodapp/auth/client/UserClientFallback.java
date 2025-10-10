package com.foodapp.auth.client;

import com.foodapp.auth.dto.UserDetails;
import com.foodapp.auth.dto.UserRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {
<<<<<<< HEAD
    @Override
    public UserDetails getUserDetails(String userId) {
        return null;
    }

    @Override
    public UserDetails getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDetails createUser(UserRegistrationRequest request) {
        return null;
    }

    @Override
    public void updateLastLogin(String userId) {
        // Do nothing in fallback
=======
    
    @Override
    public UserDetails getUserDetails(String userId) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserId(userId);
        userDetails.setUsername("fallback-user");
        return userDetails;
    }
    
    @Override
    public UserDetails getUserByEmail(String email) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(email);
        userDetails.setUsername("fallback-user");
        return userDetails;
    }
    
    @Override
    public UserDetails createUser(UserRegistrationRequest request) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUsername(request.getUsername());
        userDetails.setEmail(request.getEmail());
        return userDetails;
    }
    
    @Override
    public void updateLastLogin(String userId) {
        // Fallback - do nothing
>>>>>>> version1.4
    }
}