package com.foodapp.order.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "scheduled_orders")
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime scheduledFor;
    private String status; // SCHEDULED, CONFIRMED, PREPARING, DELIVERED, CANCELLED
    
    // Scheduling Details
    private LocalDateTime scheduledAt;
    private Boolean isRecurring;
    private String recurrencePattern; // DAILY, WEEKLY, MONTHLY
    @ElementCollection
    @CollectionTable(name = "scheduled_order_recurring_days", joinColumns = @JoinColumn(name = "scheduled_order_id"))
    @Column(name = "day_name")
    private List<String> recurringDays;
    private LocalDateTime recurrenceEndDate;
    
    // Order Details
    @Transient  // Use separate entity for order items
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private String specialInstructions;
    
    // Restaurant Confirmation
    private Boolean restaurantConfirmed;
    private LocalDateTime confirmationTime;
    private String rejectionReason;
    
    // Delivery Slot
    private String timeSlot;
    private Boolean isPriorityDelivery;
    private String deliveryPreference; // ASAP, SPECIFIC_TIME
    
    // Notifications
    private Boolean reminderSent;
    private Boolean confirmationSent;
    private LocalDateTime lastReminderSent;
    
    // Payment
    private String paymentStatus;
    private String paymentMethod;
    private Boolean prePaymentRequired;
    
    // Cancellation
    private Boolean isCancellable;
    private LocalDateTime cancellationDeadline;
    private String cancellationPolicy;
}