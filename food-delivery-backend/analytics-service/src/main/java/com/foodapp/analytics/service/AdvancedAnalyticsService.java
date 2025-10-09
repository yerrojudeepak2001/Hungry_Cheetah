package com.foodapp.analytics.service;

import com.foodapp.analytics.model.AnalyticsReport;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdvancedAnalyticsService {
    private final MongoTemplate mongoTemplate;
    private final AnalyticsRepository analyticsRepository;

    public AdvancedAnalyticsService(
            MongoTemplate mongoTemplate,
            AnalyticsRepository analyticsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.analyticsRepository = analyticsRepository;
    }

    public Map<String, Object> generateComprehensiveAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> analytics = new HashMap<>();

        // Order Analytics
        analytics.put("orderMetrics", calculateOrderMetrics(startDate, endDate));
        
        // Revenue Analytics
        analytics.put("revenueMetrics", calculateRevenueMetrics(startDate, endDate));
        
        // Customer Analytics
        analytics.put("customerMetrics", calculateCustomerMetrics(startDate, endDate));
        
        // Restaurant Analytics
        analytics.put("restaurantMetrics", calculateRestaurantMetrics(startDate, endDate));
        
        // Delivery Analytics
        analytics.put("deliveryMetrics", calculateDeliveryMetrics(startDate, endDate));
        
        // AI Performance Analytics
        analytics.put("aiMetrics", calculateAIMetrics(startDate, endDate));

        return analytics;
    }

    private Map<String, Object> calculateOrderMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("createdAt").gte(startDate).lt(endDate)
            ),
            Aggregation.group()
                .count().as("totalOrders")
                .sum("totalAmount").as("totalRevenue")
                .avg("totalAmount").as("averageOrderValue")
                .addToSet("userId").as("uniqueCustomers")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
            aggregation, "orders", Document.class);
        
        return results.getUniqueMappedResult();
    }

    private Map<String, Object> calculateRevenueMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        return mongoTemplate.aggregate(
            Aggregation.newAggregation(
                Aggregation.match(
                    Criteria.where("createdAt").gte(startDate).lt(endDate)
                ),
                Aggregation.group("restaurantId")
                    .sum("totalAmount").as("revenue")
                    .count().as("orderCount"),
                Aggregation.sort(Sort.Direction.DESC, "revenue")
            ),
            "orders",
            Document.class
        ).getMappedResults();
    }

    private Map<String, Object> calculateCustomerMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> customerMetrics = new HashMap<>();

        // Customer segmentation
        customerMetrics.put("segments", calculateCustomerSegments(startDate, endDate));
        
        // Customer retention
        customerMetrics.put("retention", calculateRetentionMetrics(startDate, endDate));
        
        // Order frequency
        customerMetrics.put("frequency", calculateOrderFrequency(startDate, endDate));

        return customerMetrics;
    }

    private List<Map<String, Object>> calculateCustomerSegments(LocalDateTime startDate, LocalDateTime endDate) {
        return mongoTemplate.aggregate(
            Aggregation.newAggregation(
                Aggregation.match(
                    Criteria.where("createdAt").gte(startDate).lt(endDate)
                ),
                Aggregation.group("userId")
                    .sum("totalAmount").as("totalSpent")
                    .count().as("orderCount"),
                Aggregation.project()
                    .and("totalSpent").as("totalSpent")
                    .and("orderCount").as("orderCount")
                    .and(new AggregationExpression() {
                        @Override
                        public Document toDocument() {
                            return Document.parse("{" +
                                "$switch: {" +
                                    "branches: [" +
                                        "{case: {$gte: ['$totalSpent', 1000]}, then: 'VIP'}," +
                                        "{case: {$gte: ['$totalSpent', 500]}, then: 'GOLD'}," +
                                        "{case: {$gte: ['$totalSpent', 100]}, then: 'SILVER'}" +
                                    "]," +
                                    "default: 'BRONZE'" +
                                "}" +
                            "}");
                        }
                    }).as("segment")
            ),
            "orders",
            Document.class
        ).getMappedResults();
    }

    private Map<String, Object> calculateRetentionMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        // Calculate monthly retention rates
        return mongoTemplate.aggregate(
            Aggregation.newAggregation(
                Aggregation.match(
                    Criteria.where("createdAt").gte(startDate).lt(endDate)
                ),
                Aggregation.project()
                    .and("userId").as("userId")
                    .and("createdAt").as("orderDate"),
                Aggregation.group("userId")
                    .push("orderDate").as("orderDates"),
                Aggregation.project()
                    .and("orderDates").size().as("totalOrders")
                    .and("orderDates").as("orderDates")
            ),
            "orders",
            Document.class
        ).getMappedResults();
    }

    private Map<String, Object> calculateRestaurantMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> restaurantMetrics = new HashMap<>();

        // Top performing restaurants
        restaurantMetrics.put("topRestaurants", findTopRestaurants(startDate, endDate));
        
        // Popular cuisines
        restaurantMetrics.put("popularCuisines", findPopularCuisines(startDate, endDate));
        
        // Peak hours by restaurant
        restaurantMetrics.put("peakHours", calculatePeakHours(startDate, endDate));

        return restaurantMetrics;
    }

    private Map<String, Object> calculateAIMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        Map<String, Object> aiMetrics = new HashMap<>();

        // Recommendation effectiveness
        aiMetrics.put("recommendationMetrics", calculateRecommendationEffectiveness(startDate, endDate));
        
        // Voice order success rate
        aiMetrics.put("voiceOrderMetrics", calculateVoiceOrderMetrics(startDate, endDate));
        
        // Weather-based conversion rate
        aiMetrics.put("weatherBasedMetrics", calculateWeatherBasedMetrics(startDate, endDate));

        return aiMetrics;
    }

    private Map<String, Object> calculateRecommendationEffectiveness(LocalDateTime startDate, LocalDateTime endDate) {
        return mongoTemplate.aggregate(
            Aggregation.newAggregation(
                Aggregation.match(
                    Criteria.where("createdAt").gte(startDate).lt(endDate)
                        .and("isRecommended").is(true)
                ),
                Aggregation.group()
                    .count().as("totalRecommendations")
                    .sum(ConditionalOperators.when(Criteria.where("ordered").is(true))
                        .then(1)
                        .otherwise(0)).as("acceptedRecommendations")
            ),
            "recommendations",
            Document.class
        ).getUniqueMappedResult();
    }

    private Map<String, Double> calculatePredictionAccuracy() {
        Map<String, Double> accuracy = new HashMap<>();
        
        // Delivery time prediction accuracy
        accuracy.put("deliveryTimePrediction", calculateDeliveryTimePredictionAccuracy());
        
        // Demand prediction accuracy
        accuracy.put("demandPrediction", calculateDemandPredictionAccuracy());
        
        // Restaurant busy hours prediction
        accuracy.put("busyHoursPrediction", calculateBusyHoursPredictionAccuracy());

        return accuracy;
    }

    private void saveAnalyticsReport(Map<String, Object> analytics, String reportType) {
        AnalyticsReport report = new AnalyticsReport();
        report.setReportType(reportType);
        report.setGeneratedAt(LocalDateTime.now());
        report.setMetrics(analytics);
        
        analyticsRepository.save(report);
    }
}