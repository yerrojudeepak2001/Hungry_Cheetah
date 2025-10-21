package com.foodapp.ai.messaging;

import com.foodapp.ai.config.AIQueues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class AIMessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(AIMessageProducer.class);
    
    private final JmsTemplate jmsTemplate;

    @Autowired
    public AIMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendRecommendationMessage(AIRecommendationMessage message) {
        try {
            jmsTemplate.convertAndSend(AIQueues.AI_RECOMMENDATION_QUEUE, message);
            logger.info("Recommendation message sent for user: {}, session: {}", 
                    message.getUserId(), message.getSessionId());
        } catch (Exception e) {
            logger.error("Failed to send recommendation message", e);
        }
    }

    public void sendNutritionAnalysisMessage(NutritionAnalysisMessage message) {
        try {
            jmsTemplate.convertAndSend(AIQueues.AI_NUTRITION_QUEUE, message);
            logger.info("Nutrition analysis message sent for user: {}, food: {}", 
                    message.getUserId(), message.getFoodItem());
        } catch (Exception e) {
            logger.error("Failed to send nutrition analysis message", e);
        }
    }

    public void sendRecipeGenerationMessage(RecipeGenerationMessage message) {
        try {
            jmsTemplate.convertAndSend(AIQueues.AI_RECIPE_QUEUE, message);
            logger.info("Recipe generation message sent for user: {}, dish: {}", 
                    message.getUserId(), message.getDishName());
        } catch (Exception e) {
            logger.error("Failed to send recipe generation message", e);
        }
    }

    public void sendUserFeedbackMessage(UserFeedbackMessage message) {
        try {
            jmsTemplate.convertAndSend(AIQueues.AI_FEEDBACK_QUEUE, message);
            logger.info("User feedback message sent for user: {}, recommendation: {}, liked: {}", 
                    message.getUserId(), message.getRecommendationId(), message.getLiked());
        } catch (Exception e) {
            logger.error("Failed to send user feedback message", e);
        }
    }

    public void sendAnalyticsMessage(Object analyticsData) {
        try {
            jmsTemplate.convertAndSend(AIQueues.AI_ANALYTICS_QUEUE, analyticsData);
            logger.debug("Analytics message sent to queue");
        } catch (Exception e) {
            logger.error("Failed to send analytics message", e);
        }
    }
}