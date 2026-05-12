package com.foodapp.order.messaging;

import com.foodapp.order.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMessageProducer {

    private final JmsTemplate jmsTemplate;

    public void sendOrderCreatedMessage(OrderCreatedMessage orderCreatedMessage) {
        try {
            jmsTemplate.convertAndSend(JmsConfig.ORDER_CREATED_QUEUE, orderCreatedMessage);
            log.info("Order created message sent for order: {}", orderCreatedMessage.getOrderId());
        } catch (Exception e) {
            log.error("Error sending order created message for order: {}", orderCreatedMessage.getOrderId(), e);
        }
    }

    public void sendOrderStatusChangedMessage(String orderId, String customerId, String customerEmail, 
                                            String restaurantId, String restaurantEmail, String oldStatus, 
                                            String newStatus, String statusMessage, String updatedBy, 
                                            String driverId, String estimatedTime, Map<String, Object> metadata) {
        try {
            OrderStatusChangedMessage message = OrderStatusChangedMessage.builder()
                    .orderId(orderId)
                    .customerId(customerId)
                    .customerEmail(customerEmail)
                    .restaurantId(restaurantId)
                    .restaurantEmail(restaurantEmail)
                    .oldStatus(oldStatus)
                    .newStatus(newStatus)
                    .statusMessage(statusMessage)
                    .updatedBy(updatedBy)
                    .driverId(driverId)
                    .estimatedTime(estimatedTime)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ORDER_STATUS_CHANGED_QUEUE, message);
            log.info("Order status changed message sent for order: {} - {} to {}", orderId, oldStatus, newStatus);
        } catch (Exception e) {
            log.error("Error sending order status changed message for order: {}", orderId, e);
        }
    }

    public void sendOrderCancelledMessage(String orderId, String customerId, String customerEmail, 
                                        String restaurantId, String restaurantEmail, String cancellationReason, 
                                        String cancelledBy, BigDecimal refundAmount, String refundMethod, 
                                        String refundStatus, Map<String, Object> metadata) {
        try {
            OrderCancelledMessage message = OrderCancelledMessage.builder()
                    .orderId(orderId)
                    .customerId(customerId)
                    .customerEmail(customerEmail)
                    .restaurantId(restaurantId)
                    .restaurantEmail(restaurantEmail)
                    .cancellationReason(cancellationReason)
                    .cancelledBy(cancelledBy)
                    .refundAmount(refundAmount)
                    .refundMethod(refundMethod)
                    .refundStatus(refundStatus)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ORDER_CANCELLED_QUEUE, message);
            log.info("Order cancelled message sent for order: {} by {}", orderId, cancelledBy);
        } catch (Exception e) {
            log.error("Error sending order cancelled message for order: {}", orderId, e);
        }
    }

    public void sendPaymentProcessedMessage(String orderId, String paymentId, String customerId, 
                                          String paymentMethod, BigDecimal amount, String currency, 
                                          String paymentStatus, String transactionId, String gatewayResponse, 
                                          Map<String, Object> paymentDetails) {
        try {
            PaymentProcessedMessage message = PaymentProcessedMessage.builder()
                    .orderId(orderId)
                    .paymentId(paymentId)
                    .customerId(customerId)
                    .paymentMethod(paymentMethod)
                    .amount(amount)
                    .currency(currency)
                    .paymentStatus(paymentStatus)
                    .transactionId(transactionId)
                    .gatewayResponse(gatewayResponse)
                    .paymentDetails(paymentDetails)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.PAYMENT_PROCESSED_QUEUE, message);
            log.info("Payment processed message sent for order: {} - status: {}", orderId, paymentStatus);
        } catch (Exception e) {
            log.error("Error sending payment processed message for order: {}", orderId, e);
        }
    }

    public void sendDeliveryAssignedMessage(String orderId, String customerId, String customerEmail, 
                                          String driverId, String driverName, String driverPhone, 
                                          String vehicleInfo, String estimatedPickupTime, 
                                          String estimatedDeliveryTime, String trackingUrl, 
                                          Map<String, Object> metadata) {
        try {
            DeliveryAssignedMessage message = DeliveryAssignedMessage.builder()
                    .orderId(orderId)
                    .customerId(customerId)
                    .customerEmail(customerEmail)
                    .driverId(driverId)
                    .driverName(driverName)
                    .driverPhone(driverPhone)
                    .vehicleInfo(vehicleInfo)
                    .estimatedPickupTime(estimatedPickupTime)
                    .estimatedDeliveryTime(estimatedDeliveryTime)
                    .trackingUrl(trackingUrl)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.DELIVERY_ASSIGNED_QUEUE, message);
            log.info("Delivery assigned message sent for order: {} - driver: {}", orderId, driverName);
        } catch (Exception e) {
            log.error("Error sending delivery assigned message for order: {}", orderId, e);
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

    public void sendOrderAnalyticsMessage(String eventType, String orderId, String customerId, String restaurantId, 
                                        String driverId, BigDecimal orderValue, String paymentMethod, 
                                        String deliveryMethod, int preparationTime, int deliveryTime, 
                                        String orderSource, Map<String, Object> eventData) {
        try {
            OrderAnalyticsMessage message = OrderAnalyticsMessage.builder()
                    .eventType(eventType)
                    .orderId(orderId)
                    .customerId(customerId)
                    .restaurantId(restaurantId)
                    .driverId(driverId)
                    .orderValue(orderValue)
                    .paymentMethod(paymentMethod)
                    .deliveryMethod(deliveryMethod)
                    .preparationTime(preparationTime)
                    .deliveryTime(deliveryTime)
                    .orderSource(orderSource)
                    .eventData(eventData)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ORDER_ANALYTICS_QUEUE, message);
            log.info("Order analytics message sent: {} for order: {}", eventType, orderId);
        } catch (Exception e) {
            log.error("Error sending order analytics message for order: {}", orderId, e);
        }
    }

    public void sendKitchenNotificationMessage(String orderId, String restaurantId, String notificationType, 
                                             String message, Map<String, Object> orderDetails) {
        try {
            Map<String, Object> kitchenNotification = Map.of(
                    "orderId", orderId,
                    "restaurantId", restaurantId,
                    "notificationType", notificationType,
                    "message", message,
                    "orderDetails", orderDetails,
                    "timestamp", System.currentTimeMillis()
            );

            jmsTemplate.convertAndSend(JmsConfig.KITCHEN_NOTIFICATION_QUEUE, kitchenNotification);
            log.info("Kitchen notification sent for order: {} - type: {}", orderId, notificationType);
        } catch (Exception e) {
            log.error("Error sending kitchen notification for order: {}", orderId, e);
        }
    }

    public void sendLoyaltyPointsMessage(String customerId, String orderId, int pointsEarned, String reason, 
                                       BigDecimal orderValue, String restaurantId) {
        try {
            Map<String, Object> loyaltyMessage = Map.of(
                    "customerId", customerId,
                    "orderId", orderId,
                    "pointsEarned", pointsEarned,
                    "reason", reason,
                    "orderValue", orderValue,
                    "restaurantId", restaurantId,
                    "timestamp", System.currentTimeMillis()
            );

            jmsTemplate.convertAndSend(JmsConfig.LOYALTY_POINTS_QUEUE, loyaltyMessage);
            log.info("Loyalty points message sent for customer: {} - points: {}", customerId, pointsEarned);
        } catch (Exception e) {
            log.error("Error sending loyalty points message for customer: {}", customerId, e);
        }
    }
}