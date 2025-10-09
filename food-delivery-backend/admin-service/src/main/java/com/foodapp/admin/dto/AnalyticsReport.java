package com.foodapp.admin.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class AnalyticsReport {
    private String reportId;
    private LocalDateTime generatedAt;
    private String reportType;
    private Map<String, Object> metrics;
    private List<DataPoint> dataPoints;
    private ReportMetadata metadata;
    
    @Data
    public static class DataPoint {
        private LocalDateTime timestamp;
        private Map<String, Double> values;
    }
    
    @Data
    public static class ReportMetadata {
        private String generatedBy;
        private String timeframe;
        private List<String> dimensions;
        private Map<String, String> filters;
    }
}