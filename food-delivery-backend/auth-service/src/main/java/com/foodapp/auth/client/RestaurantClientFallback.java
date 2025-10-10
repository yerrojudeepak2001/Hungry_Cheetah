package com.foodapp.auth.client;

import com.foodapp.auth.dto.RolePermissions;
import com.foodapp.auth.dto.StaffValidationRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    
    @Override
    public RolePermissions getRolePermissions(String restaurantId, String role) {
        RolePermissions permissions = new RolePermissions();
        permissions.setRole(role);
        permissions.setPermissions(List.of("READ"));
        return permissions;
    }
    
    @Override
    public Boolean validateStaff(StaffValidationRequest request) {
        return false;
    }
    
    @Override
    public List<String> getRestaurantsByOwner(String ownerId) {
        return List.of();
    }
}