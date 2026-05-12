package com.foodapp.analytics.repository;

import com.foodapp.analytics.model.OrderAnalytics;
import com.foodapp.analytics.dto.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderAnalyticsRepository extends MongoRepository<OrderAnalytics, String> {

    @Aggregation(pipeline = {
            "{ $match: { orderTime: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: '$restaurantId', totalOrders: { $sum: 1 }, totalRevenue: { $sum: '$orderAmount' } } }"
    })
    List<RestaurantPerformance> getRestaurantPerformance(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $match: { orderTime: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: { $dateToString: { format: '%Y-%m-%d', date: '$orderTime' } }, orderCount: { $sum: 1 } } }",
            "{ $sort: { '_id': 1 } }"
    })
    List<DailyOrderStats> getDailyOrderStats(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $match: { orderTime: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: '$deliveryZone', orderCount: { $sum: 1 }, avgDeliveryTime: { $avg: '$deliveryTime' } } }"
    })
    List<ZonePerformance> getZonePerformance(LocalDateTime start, LocalDateTime end);
}