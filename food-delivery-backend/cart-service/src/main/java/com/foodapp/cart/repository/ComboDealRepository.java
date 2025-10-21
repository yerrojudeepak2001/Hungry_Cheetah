package com.foodapp.cart.repository;

import com.foodapp.cart.entity.ComboDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComboDealRepository extends JpaRepository<ComboDeal, Long> {
    
    List<ComboDeal> findByIsActiveTrue();
    
    List<ComboDeal> findByRestaurantIdAndIsActiveTrue(String restaurantId);
    
    ComboDeal findByDealCodeAndIsActiveTrue(String dealCode);
    
    @Query("SELECT cd FROM ComboDeal cd WHERE cd.isActive = true AND " +
           "(cd.validFrom IS NULL OR cd.validFrom <= :currentTime) AND " +
           "(cd.validUntil IS NULL OR cd.validUntil >= :currentTime)")
    List<ComboDeal> findActiveDealsForTime(@Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT cd FROM ComboDeal cd WHERE cd.restaurantId = :restaurantId AND cd.isActive = true AND " +
           "(cd.validFrom IS NULL OR cd.validFrom <= :currentTime) AND " +
           "(cd.validUntil IS NULL OR cd.validUntil >= :currentTime) AND " +
           "(cd.maxTotalUses IS NULL OR cd.currentUses < cd.maxTotalUses)")
    List<ComboDeal> findAvailableDealsForRestaurant(@Param("restaurantId") String restaurantId, 
                                                   @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT cd FROM ComboDeal cd JOIN cd.requiredItemIds item WHERE item IN :itemIds AND " +
           "cd.isActive = true AND cd.restaurantId = :restaurantId")
    List<ComboDeal> findDealsByItemIds(@Param("itemIds") List<String> itemIds, 
                                     @Param("restaurantId") String restaurantId);
}