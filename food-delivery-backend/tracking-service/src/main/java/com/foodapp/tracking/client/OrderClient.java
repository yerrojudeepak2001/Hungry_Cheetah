package com.foodapp.tracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.tracking.dto.*;
import java.util.List;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}/tracking")
    OrderTrackingInfo getOrderTrackingInfo(@PathVariable("orderId") String orderId);

    @PutMapping("/api/orders/{orderId}/tracking-status")
    void updateOrderTrackingStatus(@PathVariable("orderId") String orderId,
            @RequestBody TrackingStatusUpdate update);

    @GetMapping("/api/orders/{orderId}/timeline")
    List<OrderTimelineEvent> getOrderTimeline(@PathVariable("orderId") String orderId);
}