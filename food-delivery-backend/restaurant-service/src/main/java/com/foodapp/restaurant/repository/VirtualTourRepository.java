package com.foodapp.restaurant.repository;

import com.foodapp.restaurant.model.VirtualTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualTourRepository extends JpaRepository<VirtualTour, Long> {
    Optional<VirtualTour> findByRestaurantId(Long restaurantId);
}