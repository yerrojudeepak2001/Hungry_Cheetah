package com.foodapp.common.constants;

public class AppConstants {
    // JWT Constants
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    
    // Queue Constants
    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String PAYMENT_SUCCESS_QUEUE = "payment.success.queue";
    public static final String PAYMENT_FAILURE_QUEUE = "payment.failure.queue";
    public static final String DELIVERY_COMPLETED_QUEUE = "delivery.completed.queue";
    public static final String USER_REGISTERED_QUEUE = "user.registered.queue";
    
    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}