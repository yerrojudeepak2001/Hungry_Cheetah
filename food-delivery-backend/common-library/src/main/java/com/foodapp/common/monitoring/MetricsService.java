package com.foodapp.common.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetricsService {
    private final MeterRegistry registry;
    private final ConcurrentHashMap<String, AtomicInteger> gauges = new ConcurrentHashMap<>();

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;
    }

    // Order metrics
    private final Counter totalOrdersCounter;
    private final Counter failedOrdersCounter;
    private final Timer orderProcessingTimer;
    private final Counter revenueCounter;

    // Payment metrics
    private final Counter successfulPaymentsCounter;
    private final Counter failedPaymentsCounter;
    private final Timer paymentProcessingTimer;

    // Delivery metrics
    private final Counter assignedDeliveriesCounter;
    private final Counter completedDeliveriesCounter;
    private final Timer deliveryTimeTimer;
    private final AtomicInteger activeDeliveryAgents;

    // AI metrics
    private final Counter aiRecommendationsCounter;
    private final Counter aiRecommendationAcceptedCounter;
    private final Timer aiProcessingTimer;

    // System metrics
    private final Timer apiResponseTimer;
    private final Counter errorCounter;
    private final Counter authFailureCounter;

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;

        // Initialize Order metrics
        this.totalOrdersCounter = Counter.builder("orders.total")
            .description("Total number of orders placed")
            .register(registry);

        this.failedOrdersCounter = Counter.builder("orders.failed")
            .description("Number of failed orders")
            .register(registry);

        this.orderProcessingTimer = Timer.builder("orders.processing.time")
            .description("Time taken to process orders")
            .register(registry);

        this.revenueCounter = Counter.builder("revenue.total")
            .description("Total revenue generated")
            .baseUnit("USD")
            .register(registry);

        // Initialize Payment metrics
        this.successfulPaymentsCounter = Counter.builder("payments.successful")
            .description("Number of successful payments")
            .register(registry);

        this.failedPaymentsCounter = Counter.builder("payments.failed")
            .description("Number of failed payments")
            .register(registry);

        this.paymentProcessingTimer = Timer.builder("payments.processing.time")
            .description("Time taken to process payments")
            .register(registry);

        // Initialize Delivery metrics
        this.assignedDeliveriesCounter = Counter.builder("deliveries.assigned")
            .description("Number of assigned deliveries")
            .register(registry);

        this.completedDeliveriesCounter = Counter.builder("deliveries.completed")
            .description("Number of completed deliveries")
            .register(registry);

        this.deliveryTimeTimer = Timer.builder("deliveries.time")
            .description("Time taken for delivery")
            .register(registry);

        this.activeDeliveryAgents = new AtomicInteger(0);
        Gauge.builder("deliveries.active.agents", activeDeliveryAgents, AtomicInteger::get)
            .description("Number of active delivery agents")
            .register(registry);

        // Initialize AI metrics
        this.aiRecommendationsCounter = Counter.builder("ai.recommendations.total")
            .description("Total number of AI recommendations")
            .register(registry);

        this.aiRecommendationAcceptedCounter = Counter.builder("ai.recommendations.accepted")
            .description("Number of accepted AI recommendations")
            .register(registry);

        this.aiProcessingTimer = Timer.builder("ai.processing.time")
            .description("Time taken for AI processing")
            .register(registry);

        // Initialize System metrics
        this.apiResponseTimer = Timer.builder("api.response.time")
            .description("API response time")
            .register(registry);

        this.errorCounter = Counter.builder("system.errors")
            .description("Number of system errors")
            .register(registry);

        this.authFailureCounter = Counter.builder("auth.failures")
            .description("Number of authentication failures")
            .register(registry);
    }

    // Order metrics methods
    public void incrementTotalOrders() {
        totalOrdersCounter.increment();
    }

    public void recordOrderProcessingTime(Runnable operation) {
        orderProcessingTimer.record(operation);
    }

    public void incrementRevenue(double amount) {
        revenueCounter.increment(amount);
    }

    // Payment metrics methods
    public void recordPaymentSuccess() {
        successfulPaymentsCounter.increment();
    }

    public void recordPaymentFailure() {
        failedPaymentsCounter.increment();
    }

    public void recordPaymentProcessingTime(Runnable operation) {
        paymentProcessingTimer.record(operation);
    }

    // Delivery metrics methods
    public void recordDeliveryAssigned() {
        assignedDeliveriesCounter.increment();
    }

    public void recordDeliveryCompleted() {
        completedDeliveriesCounter.increment();
    }

    public void updateActiveDeliveryAgents(int count) {
        activeDeliveryAgents.set(count);
    }

    public void recordDeliveryTime(Runnable operation) {
        deliveryTimeTimer.record(operation);
    }

    // AI metrics methods
    public void recordAiRecommendation() {
        aiRecommendationsCounter.increment();
    }

    public void recordAiRecommendationAccepted() {
        aiRecommendationAcceptedCounter.increment();
    }

    public void recordAiProcessingTime(Runnable operation) {
        aiProcessingTimer.record(operation);
    }

    // System metrics methods
    public void recordApiResponseTime(String endpoint, Runnable operation) {
        Timer.builder("api.response.time")
            .tag("endpoint", endpoint)
            .register(registry)
            .record(operation);
    }

    public void recordError(String errorType) {
        Counter.builder("system.errors")
            .tag("type", errorType)
            .register(registry)
            .increment();
    }

    public void recordAuthFailure() {
        authFailureCounter.increment();
    }

    // Custom metric methods
    public void recordCustomMetric(String name, double value, String... tags) {
        if (tags.length % 2 != 0) {
            throw new IllegalArgumentException("Tags must be provided in key-value pairs");
        }

        Counter counter = Counter.builder(name)
            .tags(tags)
            .register(registry);
        counter.increment(value);
    }

    public void recordCustomTiming(String name, Runnable operation, String... tags) {
        Timer timer = Timer.builder(name)
            .tags(tags)
            .register(registry);
        timer.record(operation);
    }

    public void updateGauge(String name, int value) {
        gauges.computeIfAbsent(name, k -> {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            Gauge.builder(k, atomicInteger, AtomicInteger::get)
                .register(registry);
            return atomicInteger;
        }).set(value);
    }
}