package com.foodapp.loyalty.repository;

import com.foodapp.loyalty.model.LoyaltyPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPoints, Long> {
    Optional<LoyaltyPoints> findByUserId(Long userId);
    List<LoyaltyPoints> findByTier(String tier);
    List<LoyaltyPoints> findByPointsGreaterThan(Integer points);
    List<LoyaltyPoints> findByIsActiveTrue();
}