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
public class RecommendationResponse {
    private Long requestId;
    private List<RecommendedItem> recommendations;
    private String algorithm;
    private Double confidence;
    private java.time.LocalDateTime generatedAt;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendedItem {
        private Long itemId;
        private String itemName;
        private Double score;
        private String reason;
        private Double price;
    }
}