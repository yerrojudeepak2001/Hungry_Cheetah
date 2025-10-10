package com.foodapp.admin.client;

import com.foodapp.admin.dto.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    
    @Override
    public List<RestaurantAdminInfo> getAllRestaurants(int page, int size) {
        return Collections.emptyList();
    }
    
    @Override
    public void updateRestaurantStatus(String restaurantId, String status) {
        // Fallback - do nothing
    }
    
    @Override
    public List<RestaurantAuditLog> getRestaurantAuditLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }
}