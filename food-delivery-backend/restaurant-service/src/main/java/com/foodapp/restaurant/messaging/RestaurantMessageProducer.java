package com.foodapp.restaurant.messaging;

import com.foodapp.restaurant.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestaurantMessageProducer {

    private final JmsTemplate jmsTemplate;

    public void sendRestaurantRegisteredMessage(String restaurantId, String restaurantName, String ownerName,
                                              String ownerEmail, String ownerPhone, String cuisine, String address,
                                              String city, String state, String zipCode, Map<String, Object> metadata) {
        try {
            RestaurantRegisteredMessage message = RestaurantRegisteredMessage.builder()
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .ownerName(ownerName)
                    .ownerEmail(ownerEmail)
                    .ownerPhone(ownerPhone)
                    .cuisine(cuisine)
                    .address(address)
                    .city(city)
                    .state(state)
                    .zipCode(zipCode)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.RESTAURANT_REGISTERED_QUEUE, message);
            log.info("Restaurant registered message sent for restaurant: {}", restaurantName);
        } catch (Exception e) {
            log.error("Error sending restaurant registered message for restaurant: {}", restaurantId, e);
        }
    }

    public void sendRestaurantStatusChangedMessage(String restaurantId, String restaurantName, String ownerEmail,
                                                 String oldStatus, String newStatus, String reason, String changedBy,
                                                 Map<String, Object> metadata) {
        try {
            RestaurantStatusChangedMessage message = RestaurantStatusChangedMessage.builder()
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .ownerEmail(ownerEmail)
                    .oldStatus(oldStatus)
                    .newStatus(newStatus)
                    .reason(reason)
                    .changedBy(changedBy)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.RESTAURANT_STATUS_CHANGED_QUEUE, message);
            log.info("Restaurant status changed message sent for restaurant: {} - {} to {}", restaurantId, oldStatus, newStatus);
        } catch (Exception e) {
            log.error("Error sending restaurant status changed message for restaurant: {}", restaurantId, e);
        }
    }

    public void sendOrderReceivedMessage(String orderId, String restaurantId, String restaurantName, String restaurantEmail,
                                       String customerId, String customerName, String customerEmail, String customerPhone,
                                       BigDecimal orderTotal, List<OrderReceivedMessage.OrderItem> items,
                                       String deliveryAddress, String specialInstructions, String paymentMethod,
                                       Map<String, Object> metadata) {
        try {
            OrderReceivedMessage message = OrderReceivedMessage.builder()
                    .orderId(orderId)
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .restaurantEmail(restaurantEmail)
                    .customerId(customerId)
                    .customerName(customerName)
                    .customerEmail(customerEmail)
                    .customerPhone(customerPhone)
                    .orderTotal(orderTotal)
                    .items(items)
                    .deliveryAddress(deliveryAddress)
                    .specialInstructions(specialInstructions)
                    .paymentMethod(paymentMethod)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ORDER_RECEIVED_QUEUE, message);
            log.info("Order received message sent for restaurant: {} - order: {}", restaurantId, orderId);
        } catch (Exception e) {
            log.error("Error sending order received message for order: {}", orderId, e);
        }
    }

    public void sendMenuUpdatedMessage(String restaurantId, String restaurantName, String ownerEmail, String updateType,
                                     List<MenuUpdatedMessage.MenuItemUpdate> updates, String updatedBy,
                                     Map<String, Object> metadata) {
        try {
            MenuUpdatedMessage message = MenuUpdatedMessage.builder()
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .ownerEmail(ownerEmail)
                    .updateType(updateType)
                    .updates(updates)
                    .updatedBy(updatedBy)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.MENU_UPDATED_QUEUE, message);
            log.info("Menu updated message sent for restaurant: {} - update type: {}", restaurantId, updateType);
        } catch (Exception e) {
            log.error("Error sending menu updated message for restaurant: {}", restaurantId, e);
        }
    }

    public void sendInventoryLowMessage(String restaurantId, String restaurantName, String ownerEmail,
                                      List<InventoryLowMessage.LowStockItem> lowStockItems, String alertLevel) {
        try {
            InventoryLowMessage message = InventoryLowMessage.builder()
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .ownerEmail(ownerEmail)
                    .lowStockItems(lowStockItems)
                    .alertLevel(alertLevel)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.INVENTORY_LOW_QUEUE, message);
            log.info("Inventory low message sent for restaurant: {} - {} items", restaurantId, lowStockItems.size());
        } catch (Exception e) {
            log.error("Error sending inventory low message for restaurant: {}", restaurantId, e);
        }
    }

    public void sendRestaurantReviewMessage(String reviewId, String restaurantId, String restaurantName, String ownerEmail,
                                          String customerId, String customerName, int rating, String reviewText,
                                          String orderId, Map<String, Object> metadata) {
        try {
            RestaurantReviewMessage message = RestaurantReviewMessage.builder()
                    .reviewId(reviewId)
                    .restaurantId(restaurantId)
                    .restaurantName(restaurantName)
                    .ownerEmail(ownerEmail)
                    .customerId(customerId)
                    .customerName(customerName)
                    .rating(rating)
                    .reviewText(reviewText)
                    .orderId(orderId)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.RESTAURANT_REVIEW_QUEUE, message);
            log.info("Restaurant review message sent for restaurant: {} - rating: {}", restaurantId, rating);
        } catch (Exception e) {
            log.error("Error sending restaurant review message for restaurant: {}", restaurantId, e);
        }
    }

    public void sendNotificationMessage(String type, String recipient, String subject, String content, Map<String, Object> metadata) {
        try {
            NotificationMessage message = NotificationMessage.builder()
                    .type(type)
                    .recipient(recipient)
                    .subject(subject)
                    .content(content)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.NOTIFICATION_QUEUE, message);
            log.info("Notification message sent: {} to {}", type, recipient);
        } catch (Exception e) {
            log.error("Error sending notification message to: {}", recipient, e);
        }
    }

    public void sendAnalyticsMessage(String eventType, String restaurantId, String customerId, Map<String, Object> eventData) {
        try {
            RestaurantAnalyticsMessage message = RestaurantAnalyticsMessage.builder()
                    .eventType(eventType)
                    .restaurantId(restaurantId)
                    .customerId(customerId)
                    .eventData(eventData)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.RESTAURANT_ANALYTICS_QUEUE, message);
            log.info("Analytics message sent: {} for restaurant: {}", eventType, restaurantId);
        } catch (Exception e) {
            log.error("Error sending analytics message for restaurant: {}", restaurantId, e);
        }
    }
}