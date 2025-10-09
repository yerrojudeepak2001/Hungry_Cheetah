package com.foodapp.admin.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReportSchedule {
    private String scheduleId;
    private String reportType;
    private String frequency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> recipients;
    private ReportParameters parameters;
    private DeliveryPreferences deliveryPreferences;
    
    @Data
    public static class ReportParameters {
        private List<String> metrics;
        private List<String> dimensions;
        private String format;
    }
    
    @Data
    public static class DeliveryPreferences {
        private String deliveryMethod;
        private String format;
        private boolean includeAttachments;
        private String notificationPreference;
    }
}