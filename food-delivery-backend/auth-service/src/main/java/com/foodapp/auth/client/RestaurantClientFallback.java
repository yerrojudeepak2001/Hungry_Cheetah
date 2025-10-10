package com.foodapp.auth.client;

import com.foodapp.auth.dto.RolePermissions;
import com.foodapp.auth.dto.StaffValidationRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    @Override
    public RolePermissions getStaffPermissions(String restaurantId, String userId) {
        return null;
    }

    @Override
    public boolean validateStaffAccess(String restaurantId, StaffValidationRequest request) {
        return false;
    }

    @Override
    public List<String> getOwnedRestaurants(String userId) {
        return Collections.emptyList();
    }
}