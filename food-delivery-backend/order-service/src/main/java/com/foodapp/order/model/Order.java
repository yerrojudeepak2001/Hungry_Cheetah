package com.foodapp.order.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String orderStatus;
    private Double totalAmount;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;
    
    @Embedded
    private DeliveryAddress deliveryAddress;
    
    private String paymentStatus;
    private String paymentMethod;
    private Boolean isGroupOrder;
    private String specialInstructions;
}