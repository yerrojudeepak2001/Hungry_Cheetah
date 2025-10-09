package com.foodapp.social.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "food_communities")
public class FoodCommunity {
    @Id
    private String id;
    private String name;
    private String description;
    private String type; // CUISINE_LOVERS, HEALTH_FOCUSED, VEGETARIAN, etc.
    
    // Community Features
    private List<Member> members;
    private List<CommunityEvent> events;
    private List<Discussion> discussions;
    private List<Recipe> sharedRecipes;
    
    // Social Dining
    private List<DiningGroup> diningGroups;
    private List<SharedOrder> sharedOrders;
    private List<MealMeetup> meetups;
    
    // Gamification
    private List<Challenge> foodChallenges;
    private List<Achievement> communityAchievements;
    private Map<String, Integer> activityPoints;
    
    @Data
    public static class Member {
        private Long userId;
        private String role; // ADMIN, MODERATOR, MEMBER
        private Integer contributionPoints;
        private List<String> badges;
        private LocalDateTime joinedDate;
        private List<String> interests;
        private Map<String, Integer> activityStats;
    }
    
    @Data
    public static class DiningGroup {
        private String groupId;
        private String name;
        private List<Long> members;
        private String cuisine;
        private LocalDateTime meetingTime;
        private String restaurantId;
        private String status;
        private Map<Long, String> rsvpStatus;
        private List<String> dietaryPreferences;
    }
    
    @Data
    public static class SharedOrder {
        private String orderId;
        private List<Long> participants;
        private String restaurantId;
        private LocalDateTime orderTime;
        private Map<Long, List<String>> individualOrders;
        private Map<Long, Double> splitAmounts;
        private String paymentStatus;
    }
    
    @Data
    public static class MealMeetup {
        private String meetupId;
        private String title;
        private String description;
        private LocalDateTime dateTime;
        private String location;
        private Integer maxParticipants;
        private List<Long> participants;
        private String cuisineType;
        private Double costPerPerson;
        private List<String> dietaryOptions;
    }
    
    @Data
    public static class Challenge {
        private String challengeId;
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String type; // CUISINE_EXPLORATION, HEALTHY_EATING, etc.
        private List<String> rules;
        private Map<Long, Integer> participantScores;
        private List<String> rewards;
    }
    
    @Data
    public static class Recipe {
        private String recipeId;
        private Long contributorId;
        private String name;
        private String description;
        private List<String> ingredients;
        private List<String> instructions;
        private String difficulty;
        private Integer preparationTime;
        private String cuisineType;
        private List<String> tags;
        private Map<String, Double> nutritionalInfo;
        private List<String> images;
        private Integer likes;
        private List<Comment> comments;
    }
}