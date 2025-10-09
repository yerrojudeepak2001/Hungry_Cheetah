package com.foodapp.admin.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ReportRequest {
    private String reportType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> metrics;
    private List<String> dimensions;
    private Map<String, String> filters;
    private String format;
    private ReportAggregation aggregation;
    
    @Data
    public static class ReportAggregation {
        private String timeGranularity;
        private List<String> groupBy;
        private Map<String, String> calculations;
    }
}