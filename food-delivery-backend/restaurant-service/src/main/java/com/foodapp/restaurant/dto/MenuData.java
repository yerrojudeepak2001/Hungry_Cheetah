package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuData {
    private Long restaurantId;
    private String menuName;
    private String category;
    private List<MenuItem> items;
    private Boolean isActive;
    private java.time.LocalDateTime lastUpdated;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuItem {
        private Long itemId;
        private String name;
        private String description;
        private Double price;
        private String category;
        private Boolean isAvailable;
        private List<String> allergens;
    }
}