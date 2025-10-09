package com.foodapp.order.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private Long menuItemId;
    private String itemName;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private String specialInstructions;
    private String customization;
}