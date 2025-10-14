package com.foodapp.user.service;

import com.foodapp.user.client.*;
import com.foodapp.user.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExternalServiceIntegrationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceIntegrationService.class);
    
    private final OrderClient orderClient;
    private final RestaurantClient restaurantClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;
    private final DeliveryClient deliveryClient;
    
    public ExternalServiceIntegrationService(
            OrderClient orderClient,
            RestaurantClient restaurantClient,
            PaymentClient paymentClient,
            NotificationClient notificationClient,
            DeliveryClient deliveryClient) {
        this.orderClient = orderClient;
        this.restaurantClient = restaurantClient;
        this.paymentClient = paymentClient;
        this.notificationClient = notificationClient;
        this.deliveryClient = deliveryClient;
    }
    
    // Order Service Integration
    public List<OrderResponse> getUserOrders(String userId) {
        try {
            return orderClient.getUserOrders(userId);
        } catch (Exception e) {
            logger.error("Error fetching user orders for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch user orders", e);
        }
    }
    
    public List<OrderResponse> getUserActiveOrders(String userId) {
        try {
            return orderClient.getUserActiveOrders(userId);
        } catch (Exception e) {
            logger.error("Error fetching active orders for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch active orders", e);
        }
    }
    
    public UserOrderStats getUserOrderStats(String userId) {
        try {
            return orderClient.getUserOrderStats(userId);
        } catch (Exception e) {
            logger.error("Error fetching order stats for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch order statistics", e);
        }
    }
    
    // Restaurant Service Integration
    public List<RestaurantResponse> getFavoriteRestaurants(String userId) {
        try {
            return restaurantClient.getUserFavoriteRestaurants(userId);
        } catch (Exception e) {
            logger.error("Error fetching favorite restaurants for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch favorite restaurants", e);
        }
    }
    
    public List<RestaurantResponse> getRecentRestaurants(String userId) {
        try {
            return restaurantClient.getUserRecentRestaurants(userId);
        } catch (Exception e) {
            logger.error("Error fetching recent restaurants for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch recent restaurants", e);
        }
    }
    
    public void addFavoriteRestaurant(String userId, String restaurantId) {
        try {
            restaurantClient.addRestaurantToFavorites(userId, restaurantId);
        } catch (Exception e) {
            logger.error("Error adding restaurant {} to favorites for user: {}", restaurantId, userId, e);
            throw new RuntimeException("Unable to add restaurant to favorites", e);
        }
    }
    
    public void removeFavoriteRestaurant(String userId, String restaurantId) {
        try {
            restaurantClient.removeRestaurantFromFavorites(userId, restaurantId);
        } catch (Exception e) {
            logger.error("Error removing restaurant {} from favorites for user: {}", restaurantId, userId, e);
            throw new RuntimeException("Unable to remove restaurant from favorites", e);
        }
    }
    
    // Payment Service Integration
    public List<PaymentMethodDto> getUserPaymentMethods(String userId) {
        try {
            return paymentClient.getUserPaymentMethods(userId);
        } catch (Exception e) {
            logger.error("Error fetching payment methods for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch payment methods", e);
        }
    }
    
    public PaymentMethodDto addPaymentMethod(String userId, PaymentMethodDto paymentMethod) {
        try {
            return paymentClient.addPaymentMethod(userId, paymentMethod);
        } catch (Exception e) {
            logger.error("Error adding payment method for user: {}", userId, e);
            throw new RuntimeException("Unable to add payment method", e);
        }
    }
    
    public void removePaymentMethod(String userId, String methodId) {
        try {
            paymentClient.removePaymentMethod(userId, methodId);
        } catch (Exception e) {
            logger.error("Error removing payment method {} for user: {}", methodId, userId, e);
            throw new RuntimeException("Unable to remove payment method", e);
        }
    }
    
    public Double getWalletBalance(String userId) {
        try {
            return paymentClient.getWalletBalance(userId);
        } catch (Exception e) {
            logger.error("Error fetching wallet balance for user: {}", userId, e);
            return 0.0; // Return default value on error
        }
    }
    
    // Notification Service Integration
    public void sendEmailNotification(NotificationDto notification) {
        try {
            notificationClient.sendEmail(notification);
        } catch (Exception e) {
            logger.error("Error sending email notification to: {}", notification.getRecipient(), e);
        }
    }
    
    public void sendSmsNotification(NotificationDto notification) {
        try {
            notificationClient.sendSms(notification);
        } catch (Exception e) {
            logger.error("Error sending SMS notification to: {}", notification.getRecipient(), e);
        }
    }
    
    public void sendPushNotification(NotificationDto notification) {
        try {
            notificationClient.sendPushNotification(notification);
        } catch (Exception e) {
            logger.error("Error sending push notification to: {}", notification.getRecipient(), e);
        }
    }
    
    public List<NotificationDto> getUserNotifications(String userId) {
        try {
            return notificationClient.getUserNotifications(userId);
        } catch (Exception e) {
            logger.error("Error fetching notifications for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch notifications", e);
        }
    }
    
    public NotificationPreferenceDto getNotificationPreferences(String userId) {
        try {
            return notificationClient.getNotificationPreferences(userId);
        } catch (Exception e) {
            logger.error("Error fetching notification preferences for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch notification preferences", e);
        }
    }
    
    public NotificationPreferenceDto updateNotificationPreferences(String userId, NotificationPreferenceDto preferences) {
        try {
            return notificationClient.updateNotificationPreferences(userId, preferences);
        } catch (Exception e) {
            logger.error("Error updating notification preferences for user: {}", userId, e);
            throw new RuntimeException("Unable to update notification preferences", e);
        }
    }
    
    // Delivery Service Integration
    public List<DeliveryTrackingDto> getActiveDeliveries(String userId) {
        try {
            return deliveryClient.getActiveDeliveries(userId);
        } catch (Exception e) {
            logger.error("Error fetching active deliveries for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch active deliveries", e);
        }
    }
    
    public List<DeliveryTrackingDto> getDeliveryHistory(String userId) {
        try {
            return deliveryClient.getDeliveryHistory(userId);
        } catch (Exception e) {
            logger.error("Error fetching delivery history for user: {}", userId, e);
            throw new RuntimeException("Unable to fetch delivery history", e);
        }
    }
    
    public DeliveryTrackingDto trackDelivery(String deliveryId) {
        try {
            return deliveryClient.trackDelivery(deliveryId);
        } catch (Exception e) {
            logger.error("Error tracking delivery: {}", deliveryId, e);
            throw new RuntimeException("Unable to track delivery", e);
        }
    }
    
    public Boolean validateDeliveryAddress(String userId, DeliveryAddressDto address) {
        try {
            return deliveryClient.validateDeliveryAddress(userId, address);
        } catch (Exception e) {
            logger.error("Error validating delivery address for user: {}", userId, e);
            return false; // Conservative approach
        }
    }
    
    public Integer getDeliveryTimeEstimate(String userId, String restaurantId, String addressId) {
        try {
            return deliveryClient.getDeliveryTimeEstimate(userId, restaurantId, addressId);
        } catch (Exception e) {
            logger.error("Error getting delivery time estimate for user: {}", userId, e);
            return 60; // Default 60 minutes
        }
    }
}