package com.foodapp.promotion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.promotion.dto.UserPromotionInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/promotion-eligibility")
    UserPromotionInfo getUserPromotionEligibility(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/segment")
    List<String> getUsersBySegment(@RequestParam String segment);
    
    @PostMapping("/api/users/{userId}/promotions")
    void assignPromotionToUser(@PathVariable("userId") String userId, 
                             @RequestBody PromotionAssignment promotion);
}