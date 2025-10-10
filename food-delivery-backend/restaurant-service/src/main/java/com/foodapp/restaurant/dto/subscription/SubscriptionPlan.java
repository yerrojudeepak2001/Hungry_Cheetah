package com.foodapp.restaurant.dto.subscription;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlan {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String billingCycle;
    private List<String> features;
    private Integer maxOrders;
    private Double commissionRate;
    private Boolean isActive;
}