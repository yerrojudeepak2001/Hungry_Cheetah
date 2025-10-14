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
@Table(name = "group_orders")
@AllArgsConstructor
@NoArgsConstructor
public class GroupOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long initiatorUserId;
    private String groupName;
    private Long restaurantId;
    private LocalDateTime deadline;
    private String status; // COLLECTING, ORDERED, DELIVERED
    
    // Participants
    @Transient  // Use separate entity or ElementCollection with Embeddable
    private List<Participant> participants;
    private Integer minParticipants;
    private Integer maxParticipants;
    private String inviteCode;
    
    // Order Details
    private BigDecimal totalAmount;
    private BigDecimal splitAmount;
    private String splitMethod; // EQUAL, CUSTOM
    
    @ElementCollection
    @CollectionTable(name = "group_order_custom_splits", joinColumns = @JoinColumn(name = "group_order_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "split_amount")
    private Map<Long, BigDecimal> customSplits;
    
    // Delivery
    private String deliveryAddress;
    private LocalDateTime estimatedDeliveryTime;
    private String deliveryInstructions;
    
    // Payment
    private String paymentStatus;
    
    @ElementCollection
    @CollectionTable(name = "group_order_payment_status", joinColumns = @JoinColumn(name = "group_order_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "payment_status")
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