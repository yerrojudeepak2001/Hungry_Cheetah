package com.foodapp.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.search.dto.UserPreferences;
import com.foodapp.search.client.fallback.UserClientFallback;
import java.util.List;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/preferences")
    UserPreferences getUserPreferences(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/{userId}/dietary-restrictions")
    List<String> getUserDietaryRestrictions(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/{userId}/favorite-cuisines")
    List<String> getUserFavoriteCuisines(@PathVariable("userId") String userId);
}