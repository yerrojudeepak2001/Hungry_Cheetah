package com.foodapp.ai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.ai.dto.AnalyticsData;

@FeignClient(name = "ANALYTICS-SERVICE", fallback = AnalyticsClientFallback.class)
public interface AnalyticsClient {
    @GetMapping("/api/analytics/training-data")
    List<AnalyticsData> getTrainingData(@RequestParam String dataType,
                                       @RequestParam LocalDateTime startDate,
                                       @RequestParam LocalDateTime endDate);
    
    @PostMapping("/api/analytics/ml-predictions")
    void submitMlPredictions(@RequestBody MLPredictionBatch predictions);
    
    @GetMapping("/api/analytics/feature-importance")
    Map<String, Double> getFeatureImportance(@RequestParam String modelType);
    
    @PostMapping("/api/analytics/model-metrics")
    void updateModelMetrics(@RequestBody ModelPerformanceMetrics metrics);
}