package com.foodapp.social.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.social.dto.UserSocialInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/social")
    UserSocialInfo getUserSocialInfo(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/search")
    List<UserBasicInfo> searchUsers(@RequestParam String query);
    
    @PutMapping("/api/users/{userId}/following")
    void updateUserFollowing(@PathVariable("userId") String userId, 
                           @RequestBody FollowUpdate update);
}