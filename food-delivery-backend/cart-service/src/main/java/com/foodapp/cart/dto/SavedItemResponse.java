package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavedItemResponse {
    private Long savedItemId;
    private String menuItemId;
    private String restaurantId;
    private String restaurantName;
    private String itemName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String customizations;
    private String specialInstructions;
    private String notes;
    private Integer priority;
    private LocalDateTime savedAt;
    private String imageUrl;
    private String category;
    private Boolean isAvailable;
    private Boolean hasBeenModified; // If price or availability changed since saving
    private BigDecimal originalPrice; // Price when saved
    private String availabilityStatus;
}