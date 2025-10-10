package com.foodapp.auth.client;

import com.foodapp.auth.dto.RolePermissions;
import com.foodapp.auth.dto.StaffValidationRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    public RolePermissions getStaffPermissions(String restaurantId, String userId) {
        return null;
    }

    public boolean validateStaffAccess(String restaurantId, StaffValidationRequest request) {
        return false;
    }

    public List<String> getOwnedRestaurants(String userId) {
        return Collections.emptyList();
    }
    
    public RolePermissions getRolePermissions(String restaurantId, String role) {
        RolePermissions permissions = new RolePermissions();
        // Return fallback permissions
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