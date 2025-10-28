package com.foodapp.order.controller;

import com.foodapp.order.dto.ApiResponse;
import com.foodapp.order.dto.OrderDTO;
import com.foodapp.order.model.GroupOrder;
import com.foodapp.order.model.ScheduledOrder;
import com.foodapp.order.model.MultiRestaurantOrder;
import com.foodapp.order.service.*;
import com.foodapp.order.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final GroupOrderService groupOrderService;
    private final ScheduledOrderService scheduledOrderService;
    private final MultiRestaurantOrderService multiRestaurantOrderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService,
            GroupOrderService groupOrderService,
            ScheduledOrderService scheduledOrderService,
            MultiRestaurantOrderService multiRestaurantOrderService,
            JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.groupOrderService = groupOrderService;
        this.scheduledOrderService = scheduledOrderService;
        this.multiRestaurantOrderService = multiRestaurantOrderService;
        this.jwtUtil = jwtUtil;
    }

    // Regular Orders
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createOrder(
            @RequestBody OrderDTO orderDTO,
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken) {
        try {
            String userId = jwtUtil.getUserIdFromHeaders(authToken, userIdHeader);
            if (userId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            orderDTO.setUserId(Long.parseLong(userId));
            var order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(new ApiResponse<>(true, "Order created successfully", order));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to create order: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrder(@PathVariable Long orderId) {
        var order = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order fetched successfully", order));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<?>> getUserOrders(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken) {
        try {
            String userId = jwtUtil.getUserIdFromHeaders(authToken, userIdHeader);
            if (userId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            var orders = orderService.getUserOrders(Long.parseLong(userId));
            return ResponseEntity.ok(new ApiResponse<>(true, "User orders fetched successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to fetch orders: " + e.getMessage(), null));
        }
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