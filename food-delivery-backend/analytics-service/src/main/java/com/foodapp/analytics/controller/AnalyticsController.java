package com.foodapp.analytics.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.analytics.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final ReportService reportService;
    private final PredictiveService predictiveService;

    public AnalyticsController(AnalyticsService analyticsService,
                             ReportService reportService,
                             PredictiveService predictiveService) {
        this.analyticsService = analyticsService;
        this.reportService = reportService;
        this.predictiveService = predictiveService;
    }

    // Business Analytics
    @GetMapping("/business/overview")
    public ResponseEntity<ApiResponse<?>> getBusinessOverview(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String region) {
        var overview = analyticsService.getBusinessOverview(timeFrame, region);
        return ResponseEntity.ok(new ApiResponse<>(true, "Business overview fetched successfully", overview));
    }

    @GetMapping("/business/revenue")
    public ResponseEntity<ApiResponse<?>> getRevenueAnalytics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String region) {
        var revenue = analyticsService.getRevenueAnalytics(timeFrame, region);
        return ResponseEntity.ok(new ApiResponse<>(true, "Revenue analytics fetched successfully", revenue));
    }

    // User Analytics
    @GetMapping("/users/behavior")
    public ResponseEntity<ApiResponse<?>> getUserBehaviorAnalytics(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String segment) {
        var behavior = analyticsService.getUserBehaviorAnalytics(timeFrame, segment);
        return ResponseEntity.ok(new ApiResponse<>(true, "User behavior analytics fetched successfully", behavior));
    }

    @GetMapping("/users/retention")
    public ResponseEntity<ApiResponse<?>> getRetentionAnalytics(@RequestParam String timeFrame) {
        var retention = analyticsService.getRetentionAnalytics(timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Retention analytics fetched successfully", retention));
    }

    // Restaurant Analytics
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> getRestaurantAnalytics(
            @PathVariable Long restaurantId,
            @RequestParam String timeFrame) {
        var analytics = analyticsService.getRestaurantAnalytics(restaurantId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant analytics fetched successfully", analytics));
    }

    // Order Analytics
    @GetMapping("/orders/trends")
    public ResponseEntity<ApiResponse<?>> getOrderTrends(
            @RequestParam String timeFrame,
            @RequestParam(required = false) String category) {
        var trends = analyticsService.getOrderTrends(timeFrame, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order trends fetched successfully", trends));
    }

    // Reports
    @PostMapping("/reports/generate")
    public ResponseEntity<ApiResponse<?>> generateReport(@RequestBody ReportRequest request) {
        var report = reportService.generateReport(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Report generated successfully", report));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ApiResponse<?>> getReport(@PathVariable String reportId) {
        var report = reportService.getReport(reportId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Report fetched successfully", report));
    }

    // Predictive Analytics
    @GetMapping("/predictive/demand")
    public ResponseEntity<ApiResponse<?>> getDemandPrediction(
            @RequestParam String region,
            @RequestParam String timeFrame) {
        var prediction = predictiveService.predictDemand(region, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Demand prediction fetched successfully", prediction));
    }

    @GetMapping("/predictive/trends")
    public ResponseEntity<ApiResponse<?>> getPredictiveTrends(@RequestParam String category) {
        var trends = predictiveService.getPredictiveTrends(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Predictive trends fetched successfully", trends));
    }

    // Real-time Analytics
    @GetMapping("/realtime/metrics")
    public ResponseEntity<ApiResponse<?>> getRealTimeMetrics() {
        var metrics = analyticsService.getRealTimeMetrics();
        return ResponseEntity.ok(new ApiResponse<>(true, "Real-time metrics fetched successfully", metrics));
    }

    @GetMapping("/realtime/alerts")
    public ResponseEntity<ApiResponse<?>> getRealTimeAlerts() {
        var alerts = analyticsService.getRealTimeAlerts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Real-time alerts fetched successfully", alerts));
    }

    // Custom Analytics
    @PostMapping("/custom")
    public ResponseEntity<ApiResponse<?>> getCustomAnalytics(@RequestBody CustomAnalyticsRequest request) {
        var analytics = analyticsService.getCustomAnalytics(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Custom analytics fetched successfully", analytics));
    }
}