package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.dto.AnalyticsReport;
import com.foodapp.admin.dto.AuditLogDTO;
import com.foodapp.admin.dto.DashboardKPIs;
import com.foodapp.admin.dto.KPIMetrics;
import com.foodapp.admin.dto.ReportRequest;
import com.foodapp.admin.service.AnalyticsServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsServiceClient analyticsServiceClient;

    @GetMapping("/dashboard-kpis")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DashboardKPIs> getDashboardKPIs(
            @RequestParam(defaultValue = "30d") String period) {
        
        DashboardKPIs kpis = analyticsServiceClient.getDashboardKPIs(period);
        return new ApiResponse<>(true, "Dashboard KPIs retrieved successfully", kpis);
    }

    @GetMapping("/kpi-metrics")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<KPIMetrics> getKPIMetrics(
            @RequestParam(defaultValue = "30d") String period) {
        
        KPIMetrics metrics = analyticsServiceClient.getKPIMetrics(period);
        return new ApiResponse<>(true, "KPI metrics retrieved successfully", metrics);
    }

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, Object>> getRevenueAnalytics(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "day") String groupBy) {
        
        Map<String, Object> analytics = analyticsServiceClient.getRevenueAnalytics(startDate, endDate, groupBy);
        return new ApiResponse<>(true, "Revenue analytics retrieved successfully", analytics);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, Object>> getOrderAnalytics(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "day") String groupBy) {
        
        Map<String, Object> analytics = analyticsServiceClient.getOrderAnalytics(startDate, endDate, groupBy);
        return new ApiResponse<>(true, "Order analytics retrieved successfully", analytics);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, Object>> getUserAnalytics(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "day") String groupBy) {
        
        Map<String, Object> analytics = analyticsServiceClient.getUserAnalytics(startDate, endDate, groupBy);
        return new ApiResponse<>(true, "User analytics retrieved successfully", analytics);
    }

    @GetMapping("/restaurants")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Map<String, Object>> getRestaurantAnalytics(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "day") String groupBy) {
        
        Map<String, Object> analytics = analyticsServiceClient.getRestaurantAnalytics(startDate, endDate, groupBy);
        return new ApiResponse<>(true, "Restaurant analytics retrieved successfully", analytics);
    }

    @GetMapping("/popular-items")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Map<String, Object>>> getPopularItems(
            @RequestParam(defaultValue = "30d") String period,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Map<String, Object>> items = analyticsServiceClient.getPopularItems(period, limit);
        return new ApiResponse<>(true, "Popular items retrieved successfully", items);
    }

    @GetMapping("/top-restaurants")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Map<String, Object>>> getTopRestaurants(
            @RequestParam(defaultValue = "30d") String period,
            @RequestParam(defaultValue = "10") int limit) {
        
        List<Map<String, Object>> restaurants = analyticsServiceClient.getTopRestaurants(period, limit);
        return new ApiResponse<>(true, "Top restaurants retrieved successfully", restaurants);
    }

    @PostMapping("/reports/generate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<AnalyticsReport> generateReport(@RequestBody ReportRequest request) {
        AnalyticsReport report = analyticsServiceClient.generateReport(request);
        return new ApiResponse<>(true, "Analytics report generated successfully", report);
    }

    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<AuditLogDTO>> getAuditLogs(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        
        List<AuditLogDTO> auditLogs = analyticsServiceClient.getAuditLogs(service, action, startDate, endDate);
        return new ApiResponse<>(true, "Audit logs retrieved successfully", auditLogs);
    }

    @GetMapping("/customer-satisfaction")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Double> getCustomerSatisfactionRate() {
        double satisfactionRate = analyticsServiceClient.getCustomerSatisfactionRate();
        return new ApiResponse<>(true, "Customer satisfaction rate retrieved successfully", satisfactionRate);
    }
}