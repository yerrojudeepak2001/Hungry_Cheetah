package com.foodapp.order.repository;

import com.foodapp.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByOrderStatus(String status);
    List<Order> findByUserIdAndOrderStatus(Long userId, String status);
}