package com.foodapp.social.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.social.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social")
public class SocialController {
    private final SocialService socialService;
    private final CommunityService communityService;
    private final EventService eventService;

    public SocialController(SocialService socialService,
                          CommunityService communityService,
                          EventService eventService) {
        this.socialService = socialService;
        this.communityService = communityService;
        this.eventService = eventService;
    }

    // Social Network
    @PostMapping("/follow/{userId}")
    public ResponseEntity<ApiResponse<?>> followUser(
            @PathVariable Long userId,
            @RequestParam Long followerUserId) {
        var followed = socialService.followUser(userId, followerUserId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User followed successfully", followed));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<ApiResponse<?>> getFollowers(@PathVariable Long userId) {
        var followers = socialService.getFollowers(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Followers fetched successfully", followers));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<ApiResponse<?>> getFollowing(@PathVariable Long userId) {
        var following = socialService.getFollowing(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Following list fetched successfully", following));
    }

    // Food Communities
    @PostMapping("/communities")
    public ResponseEntity<ApiResponse<?>> createCommunity(@RequestBody Community community) {
        var created = communityService.createCommunity(community);
        return ResponseEntity.ok(new ApiResponse<>(true, "Community created successfully", created));
    }

    @GetMapping("/communities/{communityId}")
    public ResponseEntity<ApiResponse<?>> getCommunityDetails(@PathVariable Long communityId) {
        var community = communityService.getCommunityDetails(communityId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Community details fetched successfully", community));
    }

    @PostMapping("/communities/{communityId}/join")
    public ResponseEntity<ApiResponse<?>> joinCommunity(
            @PathVariable Long communityId,
            @RequestParam Long userId) {
        var joined = communityService.joinCommunity(communityId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Joined community successfully", joined));
    }

    // Social Posts
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<?>> createPost(@RequestBody SocialPost post) {
        var created = socialService.createPost(post);
        return ResponseEntity.ok(new ApiResponse<>(true, "Post created successfully", created));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> getPost(@PathVariable Long postId) {
        var post = socialService.getPost(postId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Post fetched successfully", post));
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<ApiResponse<?>> likePost(
            @PathVariable Long postId,
            @RequestParam Long userId) {
        var liked = socialService.likePost(postId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Post liked successfully", liked));
    }

    // Food Events
    @PostMapping("/events")
    public ResponseEntity<ApiResponse<?>> createEvent(@RequestBody FoodEvent event) {
        var created = eventService.createEvent(event);
        return ResponseEntity.ok(new ApiResponse<>(true, "Event created successfully", created));
    }

    @GetMapping("/events")
    public ResponseEntity<ApiResponse<?>> getUpcomingEvents(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category) {
        var events = eventService.getUpcomingEvents(location, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Events fetched successfully", events));
    }

    @PostMapping("/events/{eventId}/register")
    public ResponseEntity<ApiResponse<?>> registerForEvent(
            @PathVariable Long eventId,
            @RequestParam Long userId) {
        var registration = eventService.registerForEvent(eventId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registered for event successfully", registration));
    }

    // Social Feed
    @GetMapping("/feed/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserFeed(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var feed = socialService.getUserFeed(userId, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "User feed fetched successfully", feed));
    }

    // Food Groups
    @PostMapping("/groups")
    public ResponseEntity<ApiResponse<?>> createFoodGroup(@RequestBody FoodGroup group) {
        var created = socialService.createFoodGroup(group);
        return ResponseEntity.ok(new ApiResponse<>(true, "Food group created successfully", created));
    }

    @GetMapping("/groups/{groupId}/members")
    public ResponseEntity<ApiResponse<?>> getGroupMembers(@PathVariable Long groupId) {
        var members = socialService.getGroupMembers(groupId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Group members fetched successfully", members));
    }
}