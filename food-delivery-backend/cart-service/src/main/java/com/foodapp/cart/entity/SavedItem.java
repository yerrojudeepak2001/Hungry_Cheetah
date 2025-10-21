package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "saved_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "menu_item_id", nullable = false)
    private String menuItemId;

    @Column(name = "restaurant_id", nullable = false)
    private String restaurantId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "customizations", columnDefinition = "TEXT")
    private String customizations;

    @Column(name = "special_instructions")
    private String specialInstructions;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    @Column(name = "notes")
    private String notes;

    @Column(name = "priority")
    @Builder.Default
    private Integer priority = 0; // For ordering saved items

    @PrePersist
    protected void onCreate() {
        savedAt = LocalDateTime.now();
    }
}