package com.foodapp.cart.repository;

import com.foodapp.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    Optional<Cart> findByUserId(String userId);
    
    Optional<Cart> findByUserIdAndIsActive(String userId, boolean isActive);
    
    @Query("SELECT c FROM Cart c WHERE c.updatedAt < :expiredTime")
    List<Cart> findAllExpired(@Param("expiredTime") LocalDateTime expiredTime);
    
    @Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.restaurantId = :restaurantId")
    Optional<Cart> findByUserIdAndRestaurantId(@Param("userId") String userId, @Param("restaurantId") String restaurantId);
    
    void deleteByUserId(String userId);
    
    @Query("SELECT COUNT(c) FROM Cart c WHERE c.userId = :userId")
    int countByUserId(@Param("userId") String userId);
}