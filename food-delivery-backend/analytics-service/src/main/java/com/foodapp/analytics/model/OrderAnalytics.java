package com.foodapp.analytics.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "order_analytics")
public class OrderAnalytics {
    @Id
    private String id;
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private Double orderAmount;
    private String orderStatus;
    private LocalDateTime orderTime;
    private String paymentMethod;
    private Map<String, Integer> itemQuantities;
    private String deliveryZone;
    private Double deliveryDistance;
    private Integer preparationTime;
    private Integer deliveryTime;
    private String promotionCode;
    private Double discountAmount;
    private Boolean isRepeatCustomer;
    private Map<String, Object> customMetrics;
}