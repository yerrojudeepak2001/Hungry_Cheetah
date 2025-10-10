package com.foodapp.cart.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceBreakdown {
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal deliveryFee;
    private BigDecimal serviceFee;
    private BigDecimal discountAmount;
    private BigDecimal discount;
    private BigDecimal total;
    private List<AppliedDiscount> appliedDiscounts;
    private String currency;
    private String errorMessage;
    
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AppliedDiscount {
        private String discountId;
        private String discountType;
        private BigDecimal discountAmount;
        private String description;
    }
}