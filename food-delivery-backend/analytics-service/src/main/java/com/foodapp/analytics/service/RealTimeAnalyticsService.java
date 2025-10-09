package com.foodapp.analytics.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

@Service
public class RealTimeAnalyticsService {
    private final MongoTemplate mongoTemplate;
    private final MetricsService metricsService;

    public RealTimeAnalyticsService(MongoTemplate mongoTemplate, MetricsService metricsService) {
        this.mongoTemplate = mongoTemplate;
        this.metricsService = metricsService;
    }

    public Map<String, Object> getRealtimeMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Active orders
        metrics.put("activeOrders", calculateActiveOrders());
        
        // Active delivery agents
        metrics.put("activeDeliveryAgents", calculateActiveDeliveryAgents());
        
        // Current revenue
        metrics.put("currentRevenue", calculateCurrentRevenue());
        
        // Popular items (last hour)
        metrics.put("trendingItems", calculateTrendingItems());
        
        // AI metrics
        metrics.put("aiMetrics", calculateAIMetrics());
        
        return metrics;
    }

    private Map<String, Object> calculateActiveOrders() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("orderStatus").in("PREPARING", "READY", "OUT_FOR_DELIVERY")
            ),
            Aggregation.group("orderStatus").count().as("count")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
            aggregation, "orders", Document.class);
        
        return results.getMappedResults().stream()
            .collect(Collectors.toMap(
                doc -> doc.getString("_id"),
                doc -> doc.getInteger("count")
            ));
    }

    private Map<String, Object> calculateAIMetrics() {
        Map<String, Object> aiMetrics = new HashMap<>();
        
        // Voice order success rate
        aiMetrics.put("voiceOrderSuccess", calculateVoiceOrderSuccessRate());
        
        // Recommendation acceptance rate
        aiMetrics.put("recommendationAcceptance", calculateRecommendationAcceptanceRate());
        
        // Weather-based order correlation
        aiMetrics.put("weatherCorrelation", calculateWeatherCorrelation());
        
        return aiMetrics;
    }

    private Double calculateVoiceOrderSuccessRate() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("createdAt").gte(LocalDateTime.now().minusHours(1))
            ),
            Aggregation.group()
                .count().as("total")
                .sum(ConditionalOperators
                    .when(Criteria.where("status").is("SUCCESS"))
                    .then(1)
                    .otherwise(0)).as("successful")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
            aggregation, "voice_order_logs", Document.class);
        
        Document result = results.getUniqueMappedResult();
        if (result != null) {
            int total = result.getInteger("total");
            int successful = result.getInteger("successful");
            return total > 0 ? (double) successful / total : 0.0;
        }
        return 0.0;
    }

    private Map<String, Integer> calculateTrendingItems() {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("createdAt").gte(LocalDateTime.now().minusHours(1))
            ),
            Aggregation.unwind("items"),
            Aggregation.group("items.menuItemId")
                .count().as("count"),
            Aggregation.sort(Sort.Direction.DESC, "count"),
            Aggregation.limit(10)
        );

        return mongoTemplate.aggregate(aggregation, "orders", Document.class)
            .getMappedResults().stream()
            .collect(Collectors.toMap(
                doc -> doc.getString("_id"),
                doc -> doc.getInteger("count")
            ));
    }

    private Map<String, Object> calculateWeatherCorrelation() {
        // TODO: Implement weather correlation analysis
        return new HashMap<>();
    }
}