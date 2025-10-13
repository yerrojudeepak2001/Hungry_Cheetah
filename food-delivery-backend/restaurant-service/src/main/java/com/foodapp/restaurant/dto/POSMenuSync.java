package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class POSMenuSync {
    private Long restaurantId;
    private String syncType;
    private Map<String, Object> menuData;
    private java.time.LocalDateTime syncTime;
    private String status;
    private String message;
}