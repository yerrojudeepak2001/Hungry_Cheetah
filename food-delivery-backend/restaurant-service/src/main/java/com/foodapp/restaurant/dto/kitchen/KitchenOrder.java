package com.foodapp.restaurant.dto.kitchen;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KitchenOrder {
    private Long orderId;
    private List<String> items;
    private String specialInstructions;
    private LocalDateTime requiredBy;
}