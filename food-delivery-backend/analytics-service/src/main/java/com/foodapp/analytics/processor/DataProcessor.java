package com.foodapp.analytics.processor;

import org.springframework.stereotype.Component;
import com.foodapp.analytics.service.MetricsCalculator;
import com.foodapp.analytics.service.TrendAnalyzer;
import java.util.Map;

@Component
public class DataProcessor {
    private final MetricsCalculator metricsCalculator;
    private final TrendAnalyzer trendAnalyzer;

    public DataProcessor(MetricsCalculator metricsCalculator, TrendAnalyzer trendAnalyzer) {
        this.metricsCalculator = metricsCalculator;
        this.trendAnalyzer = trendAnalyzer;
    }

    public Map<String, Object> processOrderData(String timeFrame) {
        return Map.of(
                "timeFrame", timeFrame,
                "status", "processed",
                "orders", metricsCalculator.calculateOrderMetrics());
    }

    public Map<String, Object> processAnalyticsData(String dataType) {
        return Map.of(
                "dataType", dataType,
                "status", "processed");
    }
}