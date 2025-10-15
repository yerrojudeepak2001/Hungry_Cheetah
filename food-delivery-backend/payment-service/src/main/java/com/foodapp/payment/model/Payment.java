package com.foodapp.payment.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long orderId;
    private Long userId;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime paymentTime;
    private String currency;
    private String paymentGateway;
    private String errorMessage;
    private Boolean isRefunded;
    private LocalDateTime refundTime;
    private String refundReason;
    
    @Embedded
    private PaymentMetadata metadata;
}