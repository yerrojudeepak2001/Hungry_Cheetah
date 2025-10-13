package com.foodapp.order.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "multi_restaurant_orders")
@AllArgsConstructor
@NoArgsConstructor
public class MultiRestaurantOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDateTime orderTime;
    private String status; // PROCESSING, CONFIRMED, IN_DELIVERY, COMPLETED
    
    // Sub-Orders
    private Map<Long, RestaurantSubOrder> restaurantOrders; // restaurantId -> order
    private Integer totalRestaurants;
    
    // Delivery
    private String deliveryAddress;
    private List<DeliveryBatch> deliveryBatches;
    private String consolidationPoint; // For multi-pickup consolidation
    private Boolean requiresConsolidation;
    
    // Pricing
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal platformFee;
    private BigDecimal taxes;
    private BigDecimal totalAmount;
    private Map<Long, BigDecimal> restaurantSubtotals;
    
    // Optimization
    private Boolean isOptimizedRoute;
    private String routeOptimizationStrategy;
    private Map<String, Integer> estimatedTimes;
    
    @Data
    public static class RestaurantSubOrder {
        private Long restaurantId;
        private String status;
        private List<OrderItem> items;
        private BigDecimal subtotal;
        private LocalDateTime preparationStartTime;
        private LocalDateTime estimatedReadyTime;
        private String preparationStatus;
        private Boolean isConfirmed;
    }
    
    @Data
    public static class DeliveryBatch {
        private String batchId;
        private List<Long> restaurantIds;
        private String status;
        private Long assignedDriverId;
        private LocalDateTime pickupTime;
        private LocalDateTime estimatedDeliveryTime;
        private String routeOptimization;
        private List<String> pickupSequence;
    }
    
    // For real-time tracking
    @Data
    public static class OrderTracking {
        private String trackingId;
        private Map<Long, String> restaurantOrderStatus;
        private Map<String, LocalDateTime> statusTimestamps;
        private List<TrackingUpdate> updates;
    }
    
    @Data
    public static class TrackingUpdate {
        private LocalDateTime timestamp;
        private String status;
        private String description;
        private Long restaurantId;
        private String driverId;
        private Map<String, Object> additionalInfo;
    }
}