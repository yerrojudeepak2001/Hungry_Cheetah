package com.foodapp.ai.messaging;

import com.foodapp.ai.config.AIQueues;
import com.foodapp.ai.service.AIEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class AIMessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AIMessageConsumer.class);
    
    private final AIEmailService emailService;

    @Autowired
    public AIMessageConsumer(AIEmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = AIQueues.AI_RECOMMENDATION_QUEUE)
    public void handleRecommendationMessage(AIRecommendationMessage message) {
        logger.info("Processing recommendation message for user: {}, session: {}", 
                message.getUserId(), message.getSessionId());
        
        try {
            // Process recommendation analytics
            logger.debug("Recommendation generated - Mood: {}, Occasion: {}, Count: {}, Confidence: {}", 
                    message.getMood(), message.getOccasion(), 
                    message.getRecommendationCount(), message.getConfidenceScore());
            
            // You can add additional processing here like:
            // - Send notifications to other services
            // - Update user engagement metrics
            // - Trigger follow-up actions
            
        } catch (Exception e) {
            logger.error("Error processing recommendation message for user: " + message.getUserId(), e);
        }
    }

    @JmsListener(destination = AIQueues.AI_NUTRITION_QUEUE)
    public void handleNutritionAnalysisMessage(NutritionAnalysisMessage message) {
        logger.info("Processing nutrition analysis message for user: {}, food: {}", 
                message.getUserId(), message.getFoodItem());
        
        try {
            // Process nutrition analysis
            logger.debug("Nutrition analysis requested for: {}", message.getFoodItem());
            
            // Additional processing could include:
            // - Tracking popular nutrition queries
            // - Building user health profiles
            // - Suggesting related healthy options
            
        } catch (Exception e) {
            logger.error("Error processing nutrition analysis message for user: " + message.getUserId(), e);
        }
    }

    @JmsListener(destination = AIQueues.AI_RECIPE_QUEUE)
    public void handleRecipeGenerationMessage(RecipeGenerationMessage message) {
        logger.info("Processing recipe generation message for user: {}, dish: {}", 
                message.getUserId(), message.getDishName());
        
        try {
            // Process recipe generation
            logger.debug("Recipe generated for: {} (servings: {}, difficulty: {})", 
                    message.getDishName(), message.getServings(), message.getDifficulty());
            
            // Additional processing:
            // - Track popular recipes
            // - Suggest ingredient shopping lists
            // - Connect with local suppliers
            
        } catch (Exception e) {
            logger.error("Error processing recipe generation message for user: " + message.getUserId(), e);
        }
    }

    @JmsListener(destination = AIQueues.AI_FEEDBACK_QUEUE)
    public void handleUserFeedbackMessage(UserFeedbackMessage message) {
        logger.info("Processing user feedback message for user: {}, recommendation: {}, liked: {}", 
                message.getUserId(), message.getRecommendationId(), message.getLiked());
        
        try {
            // Process user feedback for AI learning
            if (Boolean.TRUE.equals(message.getLiked())) {
                logger.debug("Positive feedback received for recommendation: {}", message.getRecommendationId());
                // Reinforce successful patterns
            } else if (Boolean.FALSE.equals(message.getLiked())) {
                logger.debug("Negative feedback received for recommendation: {}", message.getRecommendationId());
                // Adjust recommendation algorithms
            }
            
            // Send thank you email (commented out to avoid spam during development)
            // emailService.sendFeedbackThankYouEmail("user@example.com", "User");
            
        } catch (Exception e) {
            logger.error("Error processing user feedback message for user: " + message.getUserId(), e);
        }
    }

    @JmsListener(destination = AIQueues.AI_ANALYTICS_QUEUE)
    public void handleAnalyticsMessage(Object analyticsData) {
        logger.debug("Processing AI analytics message");
        
        try {
            // Process analytics data
            logger.trace("Analytics data received: {}", analyticsData);
            
            // Additional processing:
            // - Real-time dashboard updates
            // - Performance monitoring
            // - Usage pattern analysis
            
        } catch (Exception e) {
            logger.error("Error processing analytics message", e);
        }
    }
}