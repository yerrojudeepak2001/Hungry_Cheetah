package com.foodapp.restaurant.repository;

import com.foodapp.restaurant.model.ARMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ARMenuRepository extends JpaRepository<ARMenu, Long> {
    Optional<ARMenu> findByRestaurantId(Long restaurantId);
}