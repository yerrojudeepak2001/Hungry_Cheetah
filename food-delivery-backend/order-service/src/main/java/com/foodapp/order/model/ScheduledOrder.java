package com.foodapp.order.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "scheduled_orders")
public class ScheduledOrder {
    @Id
    private String id;
    private Long orderId;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime scheduledFor;
    private String status; // SCHEDULED, CONFIRMED, PREPARING, DELIVERED, CANCELLED
    
    // Scheduling Details
    private LocalDateTime scheduledAt;
    private Boolean isRecurring;
    private String recurrencePattern; // DAILY, WEEKLY, MONTHLY
    private List<String> recurringDays;
    private LocalDateTime recurrenceEndDate;
    
    // Order Details
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