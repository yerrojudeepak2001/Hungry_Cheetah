package com.foodapp.notification.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmartAlertConfig {
    @NotNull
    private Long userId;
    
    @Builder.Default
    private Boolean enableSmartTiming = true;
    
    @Builder.Default
    private Boolean enableFrequencyControl = true;
    
    @Builder.Default
    private Boolean enableContentOptimization = true;
    
    @Builder.Default
    private Boolean enableChannelOptimization = true;
    
    // Machine learning preferences
    @Builder.Default
    private Boolean allowMLOptimization = true;
    
    @Builder.Default
    private Boolean personalizeContent = true;
    
    // Smart timing settings
    @Builder.Default
    private Integer maxDailyNotifications = 10;
    
    @Builder.Default
    private Integer minTimeBetweenNotifications = 30; // minutes
    
    // Priority thresholds
    @Builder.Default
    private Double urgentThreshold = 0.8;
    
    @Builder.Default
    private Double highThreshold = 0.6;
    
    @Builder.Default
    private Double mediumThreshold = 0.4;
    
    // Custom rules
    private Map<String, Object> customRules;
}