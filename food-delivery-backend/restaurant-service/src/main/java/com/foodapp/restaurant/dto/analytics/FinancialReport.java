package com.foodapp.restaurant.dto.analytics;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialReport {
    private Long restaurantId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;
    private BigDecimal platformFees;
    private BigDecimal deliveryFees;
    private Map<String, BigDecimal> revenueByPaymentMethod;
    private Map<String, BigDecimal> expensesByCategory;
    private BigDecimal taxAmount;
}