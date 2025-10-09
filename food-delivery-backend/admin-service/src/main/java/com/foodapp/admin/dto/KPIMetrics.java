package com.foodapp.admin.dto;

import lombok.Data;
import java.util.Map;

@Data
public class KPIMetrics {
    private String metricName;
    private Double currentValue;
    private Double previousValue;
    private Double changePercentage;
    private String trend;
    private Map<String, Double> breakdownByDimension;
    private ThresholdInfo thresholds;
    private MetricMetadata metadata;
    
    @Data
    public static class ThresholdInfo {
        private Double warningThreshold;
        private Double criticalThreshold;
        private String status;
    }
    
    @Data
    public static class MetricMetadata {
        private String unit;
        private String category;
        private String description;
        private Map<String, String> tags;
    }
}