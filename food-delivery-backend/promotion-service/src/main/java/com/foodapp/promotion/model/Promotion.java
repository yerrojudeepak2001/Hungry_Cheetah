package com.foodapp.promotion.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "promotions")
public class Promotion {
    @Id
    private String id;
    private String code;
    private String name;
    private String description;
    private String type; // PERCENTAGE_OFF, FLAT_OFF, FREE_DELIVERY, BUY_ONE_GET_ONE
    private BigDecimal discountValue;
    private BigDecimal minimumOrderValue;
    private BigDecimal maximumDiscount;
    
    // Validity
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Integer maxUses;
    private Integer currentUses;
    private Integer usesPerUser;
    
    // Targeting
    private List<String> applicableRestaurants;
    private List<String> applicableCuisines;
    private List<String> applicableItems;
    private List<String> userSegments;
    private Map<String, Object> conditions;
    
    // Time-based
    private List<String> applicableDays;
    private String startTime;
    private String endTime;
    private Boolean isHappyHour;
    
    // Stacking Rules
    private Boolean canStackWithOtherOffers;
    private List<String> excludedPromotions;
    private Integer stackingPriority;
    
    // Payment Method
    private List<String> validPaymentMethods;
    private Boolean firstTimeUserOnly;
    
    // Analytics
    private Integer totalRedemptions;
    private BigDecimal totalDiscountGiven;
    private Map<String, Integer> redemptionsBySegment;
}