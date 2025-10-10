package com.foodapp.order.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.common.dto.OrderDTO;
import com.foodapp.order.model.GroupOrder;
import com.foodapp.order.model.ScheduledOrder;
import com.foodapp.order.model.MultiRestaurantOrder;
import com.foodapp.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final GroupOrderService groupOrderService;
    private final ScheduledOrderService scheduledOrderService;
    private final MultiRestaurantOrderService multiRestaurantOrderService;

    public OrderController(OrderService orderService,
                         GroupOrderService groupOrderService,
                         ScheduledOrderService scheduledOrderService,
                         MultiRestaurantOrderService multiRestaurantOrderService) {
        this.orderService = orderService;
        this.groupOrderService = groupOrderService;
        this.scheduledOrderService = scheduledOrderService;
        this.multiRestaurantOrderService = multiRestaurantOrderService;
    }

    // Regular Orders
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(@RequestBody OrderDTO orderDTO) {
        var order = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created successfully", order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrder(@PathVariable Long orderId) {
        var order = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order fetched successfully", order));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<?>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order status updated successfully", null));
    }

    // Group Orders
    @PostMapping("/group")
    public ResponseEntity<ApiResponse<?>> createGroupOrder(@RequestBody GroupOrder groupOrder) {
        var order = groupOrderService.createGroupOrder(groupOrder);
        return ResponseEntity.ok(new ApiResponse<>(true, "Group order created successfully", order));
    }

    @PostMapping("/group/{orderId}/join")
    public ResponseEntity<ApiResponse<?>> joinGroupOrder(
            @PathVariable String orderId,
            @RequestParam Long userId) {
        var order = groupOrderService.addParticipant(orderId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Joined group order successfully", order));
    }

    @PostMapping("/group/{orderId}/items")
    public ResponseEntity<ApiResponse<?>> addGroupOrderItems(
            @PathVariable String orderId,
            @RequestParam Long userId,
            @RequestBody List<GroupOrder.GroupOrderItem> items) {
        var order = groupOrderService.addItems(orderId, userId, items);
        return ResponseEntity.ok(new ApiResponse<>(true, "Items added to group order", order));
    }

    // Scheduled Orders
    @PostMapping("/scheduled")
    public ResponseEntity<ApiResponse<?>> scheduleOrder(@RequestBody ScheduledOrder scheduledOrder) {
        var order = scheduledOrderService.scheduleOrder(scheduledOrder);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order scheduled successfully", order));
    }

    @GetMapping("/scheduled/{userId}")
    public ResponseEntity<ApiResponse<?>> getScheduledOrders(@PathVariable Long userId) {
        var orders = scheduledOrderService.getUserScheduledOrders(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Scheduled orders fetched successfully", orders));
    }

    @DeleteMapping("/scheduled/{orderId}")
    public ResponseEntity<ApiResponse<?>> cancelScheduledOrder(@PathVariable String orderId) {
        scheduledOrderService.cancelScheduledOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Scheduled order cancelled successfully", null));
    }

    // Multi-Restaurant Orders
    @PostMapping("/multi-restaurant")
    public ResponseEntity<ApiResponse<?>> createMultiRestaurantOrder(
            @RequestBody MultiRestaurantOrder order) {
        var createdOrder = multiRestaurantOrderService.createOrder(order);
        return ResponseEntity.ok(new ApiResponse<>(true, "Multi-restaurant order created successfully", createdOrder));
    }

    @GetMapping("/multi-restaurant/{orderId}")
    public ResponseEntity<ApiResponse<?>> getMultiRestaurantOrder(@PathVariable String orderId) {
        var order = multiRestaurantOrderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Multi-restaurant order fetched successfully", order));
    }

    @GetMapping("/multi-restaurant/{orderId}/tracking")
    public ResponseEntity<ApiResponse<?>> getMultiRestaurantOrderTracking(@PathVariable String orderId) {
        var tracking = multiRestaurantOrderService.getOrderTracking(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order tracking fetched successfully", tracking));
    }
}