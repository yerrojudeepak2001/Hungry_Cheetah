package com.foodapp.loyalty.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.loyalty.dto.PromotionInfo;

@FeignClient(name = "PROMOTION-SERVICE", fallback = PromotionClientFallback.class)
public interface PromotionClient {
    @GetMapping("/api/promotions/loyalty/{tier}")
    List<PromotionInfo> getLoyaltyPromotions(@PathVariable("tier") String tier);
    
    @PostMapping("/api/promotions/loyalty/apply")
    PromotionApplication applyLoyaltyPromotion(@RequestBody PromotionRequest request);
    
    @GetMapping("/api/promotions/user/{userId}/available")
    List<PromotionInfo> getUserAvailablePromotions(@PathVariable("userId") String userId);
    
    @PostMapping("/api/promotions/points/convert")
    PointsConversion convertPointsToPromotion(@RequestBody PointsConversionRequest request);
}