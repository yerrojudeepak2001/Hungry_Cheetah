package com.foodapp.analytics.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportRequest {
    private String reportType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> metrics;
    private Map<String, Object> filters;
    private String format;
    private Boolean includeGraphs;

    public ReportRequest() {
    }

    public ReportRequest(String reportType, LocalDateTime startDate, LocalDateTime endDate) {
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Boolean getIncludeGraphs() {
        return includeGraphs;
    }

    public void setIncludeGraphs(Boolean includeGraphs) {
        this.includeGraphs = includeGraphs;
    }
}