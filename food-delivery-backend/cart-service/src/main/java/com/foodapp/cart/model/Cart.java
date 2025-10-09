package com.foodapp.cart.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private Long userId;
    private Long restaurantId;
    private List<CartItem> items;
    private BigDecimal totalAmount;
    private LocalDateTime lastUpdated;
    private Boolean isActive;
}