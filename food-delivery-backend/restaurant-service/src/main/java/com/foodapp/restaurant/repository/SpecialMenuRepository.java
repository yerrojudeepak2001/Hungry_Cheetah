package com.foodapp.restaurant.repository;

import com.foodapp.restaurant.model.SpecialMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecialMenuRepository extends JpaRepository<SpecialMenu, Long> {
    
    List<SpecialMenu> findByRestaurantId(Long restaurantId);
    
    List<SpecialMenu> findByRestaurantIdAndIsActiveTrue(Long restaurantId);
    
    List<SpecialMenu> findByRestaurantIdAndEndDateAfterAndStartDateBefore(
        Long restaurantId, LocalDateTime now, LocalDateTime nowAgain);
}