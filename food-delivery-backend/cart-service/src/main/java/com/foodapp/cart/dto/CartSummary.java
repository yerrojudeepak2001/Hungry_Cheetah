package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartSummary {
    private Long cartId;
    private String userId;
    private String restaurantId;
    private String restaurantName;
    private Integer totalItems;
    private Integer uniqueItems;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal deliveryFee;
    private BigDecimal serviceFee;
    private BigDecimal totalDiscounts;
    private BigDecimal finalTotal;
    private String currency;
    private List<CartItemSummary> items;
    private List<AppliedDiscountSummary> appliedDiscounts;
    private List<String> suggestions;
    private List<ComboDealsummary> availableDeals;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime expiresAt;
    private Boolean isActive;
    private String status; // ACTIVE, EXPIRED, CHECKED_OUT, ABANDONED

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CartItemSummary {
        private Long itemId;
        private String menuItemId;
        private String itemName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private String customizations;
        private String specialInstructions;
        private Boolean isAvailable;
        private String imageUrl;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppliedDiscountSummary {
        private String discountId;
        private String discountCode;
        private String discountType;
        private BigDecimal discountAmount;
        private String description;
        private Boolean isAutomatic;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ComboDealsummary {
        private String dealId;
        private String dealName;
        private String description;
        private BigDecimal potentialSavings;
        private List<String> requiredItems;
        private Boolean isApplicable;
    }
}