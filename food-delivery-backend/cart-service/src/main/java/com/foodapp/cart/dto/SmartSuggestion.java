package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmartSuggestion {
    private String suggestionId;
    private String suggestionType; // FREQUENTLY_BOUGHT_TOGETHER, TRENDING, CHEF_SPECIAL, COMBO_UPGRADE, SIMILAR_ITEMS
    private String title;
    private String description;
    private List<SuggestedItem> items;
    private BigDecimal totalPrice;
    private BigDecimal savings;
    private String reasoning; // Why this suggestion is being made
    private Double confidence; // Confidence score (0.0 to 1.0)
    private Integer priority; // For ordering suggestions
    private LocalDateTime validUntil;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SuggestedItem {
        private String menuItemId;
        private String itemName;
        private String description;
        private BigDecimal price;
        private String imageUrl;
        private String category;
        private Double rating;
        private Integer reviewCount;
        private Boolean isPopular;
        private Boolean isNew;
        private String specialOffer;
    }
}