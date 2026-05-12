package com.foodapp.cart.repository;

import com.foodapp.cart.entity.SavedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedItemRepository extends JpaRepository<SavedItem, Long> {
    
    List<SavedItem> findByUserIdOrderByPriorityDescSavedAtDesc(String userId);
    
    Optional<SavedItem> findByUserIdAndMenuItemIdAndRestaurantId(String userId, String menuItemId, String restaurantId);
    
    List<SavedItem> findByUserIdAndRestaurantId(String userId, String restaurantId);
    
    @Query("SELECT COUNT(si) FROM SavedItem si WHERE si.userId = :userId")
    Long countByUserId(@Param("userId") String userId);
    
    @Query("SELECT si FROM SavedItem si WHERE si.userId = :userId AND " +
           "(si.itemName LIKE %:searchTerm% OR si.notes LIKE %:searchTerm%)")
    List<SavedItem> findByUserIdAndSearchTerm(@Param("userId") String userId, 
                                            @Param("searchTerm") String searchTerm);
    
    void deleteByUserIdAndMenuItemIdAndRestaurantId(String userId, String menuItemId, String restaurantId);
    
    @Query("SELECT DISTINCT si.restaurantId FROM SavedItem si WHERE si.userId = :userId")
    List<String> findDistinctRestaurantIdsByUserId(@Param("userId") String userId);
}