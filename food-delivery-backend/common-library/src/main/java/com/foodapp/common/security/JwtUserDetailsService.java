package com.foodapp.common.security;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public JwtUserDetailsService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Make a call to the auth service to get user details
            List<ServiceInstance> instances = discoveryClient.getInstances("AUTH-SERVICE");
            if (instances == null || instances.isEmpty()) {
                throw new RuntimeException("AUTH-SERVICE not available");
            }
            
            ServiceInstance authService = instances.get(0);
            String userInfoUrl = authService.getUri() + "/api/auth/user/" + username;
            
            ResponseEntity<UserDetailsResponse> response = restTemplate.getForEntity(
                userInfoUrl, 
                UserDetailsResponse.class);

            if (response != null && response.getBody() != null) {
                UserDetailsResponse userDetails = response.getBody();
                List<SimpleGrantedAuthority> authorities = userDetails.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

                return new User(userDetails.getUsername(), "", authorities);
            }
            
            throw new UsernameNotFoundException("User not found with username: " + username);
        } catch (Exception e) {
            // Fallback for non-critical services - just authenticate with username and no authorities
            // In a real application, you might want more sophisticated error handling
            return new User(username, "", Collections.emptyList());
        }
    }
    
    public static class UserDetailsResponse {
        private String username;
        private List<String> roles;
        
        public UserDetailsResponse() {
        }
        
        public String getUsername() {
            return username;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public List<String> getRoles() {
            return roles;
        }
        
        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}