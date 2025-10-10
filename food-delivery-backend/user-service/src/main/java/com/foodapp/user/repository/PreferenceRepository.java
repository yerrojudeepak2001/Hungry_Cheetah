package com.foodapp.user.repository;

import com.foodapp.user.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    
    List<Preference> findByUserId(Long userId);
    
    List<Preference> findByUserIdAndCategory(Long userId, String category);
    
    List<Preference> findByUserIdAndActive(Long userId, Boolean active);
    
    @Query("SELECT p FROM Preference p WHERE p.user.id = :userId AND p.type = :type")
    List<Preference> findByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
    
    @Query("SELECT p FROM Preference p WHERE p.user.id = :userId AND p.category = :category AND p.active = true")
    List<Preference> findActivePreferencesByUserIdAndCategory(@Param("userId") Long userId, @Param("category") String category);
    
    void deleteByUserId(Long userId);
}