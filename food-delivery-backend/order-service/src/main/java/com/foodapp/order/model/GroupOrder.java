package com.foodapp.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "group_orders")
public class GroupOrder {
    @Id
    private String id;
    private Long initiatorUserId;
    private String groupName;
    private Long restaurantId;
    private LocalDateTime deadline;
    private String status; // COLLECTING, ORDERED, DELIVERED
    
    // Participants
    private List<Participant> participants;
    private Integer minParticipants;
    private Integer maxParticipants;
    private String inviteCode;
    
    // Order Details
    private BigDecimal totalAmount;
    private BigDecimal splitAmount;
    private String splitMethod; // EQUAL, CUSTOM
    private Map<Long, BigDecimal> customSplits;
    
    // Delivery
    private String deliveryAddress;
    private LocalDateTime estimatedDeliveryTime;
    private String deliveryInstructions;
    
    // Payment
    private String paymentStatus;
    private Map<Long, String> participantPaymentStatus;
    
    @Data
    public static class Participant {
        private Long userId;
        private String name;
        private List<GroupOrderItem> items;
        private BigDecimal subtotal;
        private String status; // JOINED, ORDERED, PAID
        private LocalDateTime joinedAt;
    }
    
    @Data
    public static class GroupOrderItem {
        private Long menuItemId;
        private String itemName;
        private Integer quantity;
        private BigDecimal price;
        private String specialInstructions;
        private Boolean isCustomized;
        private Map<String, String> customizations;
    }
}