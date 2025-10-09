package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.restaurant.dto.FeedbackData;
import com.foodapp.restaurant.dto.RatingInfo;

@FeignClient(name = "FEEDBACK-SERVICE", fallback = FeedbackClientFallback.class)
public interface FeedbackClient {
    @GetMapping("/api/feedback/restaurant/{restaurantId}")
    List<FeedbackData> getRestaurantFeedback(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/feedback/delivery/{deliveryId}")
    List<FeedbackData> getDeliveryFeedback(@PathVariable("deliveryId") String deliveryId);
    
    @PostMapping("/api/feedback/restaurant")
    void submitRestaurantFeedback(@RequestBody FeedbackData feedback);
    
    @GetMapping("/api/ratings/aggregate/{restaurantId}")
    RatingInfo getAggregateRatings(@PathVariable("restaurantId") String restaurantId);
}