package com.foodapp.analytics.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataProcessor {
    private final SparkSession sparkSession;
    private final MetricsCalculator metricsCalculator;
    private final TrendAnalyzer trendAnalyzer;

    public Map<String, Object> processOrderData(LocalDateTime startDate, LocalDateTime endDate) {
        // Load order data
        Dataset<Row> orders = sparkSession.sql(
            "SELECT * FROM orders WHERE order_date BETWEEN '" + startDate + "' AND '" + endDate + "'");

        // Calculate basic metrics
        Map<String, Object> basicMetrics = metricsCalculator.calculateOrderMetrics(orders);

        // Analyze trends
        Map<String, Object> trends = trendAnalyzer.analyzeTrends(orders);

        // Calculate advanced metrics
        Map<String, Object> advancedMetrics = calculateAdvancedMetrics(orders);

        // Combine all metrics
        Map<String, Object> result = new HashMap<>();
        result.putAll(basicMetrics);
        result.putAll(trends);
        result.putAll(advancedMetrics);

        return result;
    }

    private Map<String, Object> calculateAdvancedMetrics(Dataset<Row> orders) {
        Map<String, Object> metrics = new HashMap<>();

        // Customer segmentation
        Dataset<Row> customerSegments = orders.groupBy("user_id")
            .agg(
                functions.count("*").as("total_orders"),
                functions.sum("order_amount").as("total_spent")
            )
            .withColumn("customer_segment", functions.when(
                functions.col("total_spent").gt(1000), "VIP"
            ).when(
                functions.col("total_spent").gt(500), "Regular"
            ).otherwise("New"));

        // Peak hours analysis
        Dataset<Row> peakHours = orders.groupBy(functions.hour("order_time").as("hour"))
            .count()
            .orderBy(functions.col("count").desc());

        // Popular items
        Dataset<Row> popularItems = orders.select(functions.explode("items").as("item"))
            .groupBy("item.name")
            .count()
            .orderBy(functions.col("count").desc());

        // Add metrics to result
        metrics.put("customerSegments", customerSegments.collectAsList());
        metrics.put("peakHours", peakHours.collectAsList());
        metrics.put("popularItems", popularItems.collectAsList());

        return metrics;
    }
}