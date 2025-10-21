package com.foodapp.ai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.ai.dto.UserBehaviorData;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/behavior")
    UserBehaviorData getUserBehaviorData(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/{userId}/preferences")
    UserPreferences getUserPreferences(@PathVariable("userId") String userId);
    
    @PutMapping("/api/users/{userId}/recommendations")
    void updateUserRecommendations(@PathVariable("userId") String userId, 
                                 @RequestBody List<RecommendationData> recommendations);
}