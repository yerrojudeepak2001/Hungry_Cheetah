package com.foodapp.cart.repository;

import com.foodapp.cart.entity.CartPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartPreferencesRepository extends JpaRepository<CartPreferences, Long> {
    
    Optional<CartPreferences> findByUserId(String userId);
    
    @Query("SELECT cp FROM CartPreferences cp WHERE cp.userId = :userId")
    Optional<CartPreferences> findPreferencesByUserId(@Param("userId") String userId);
    
    boolean existsByUserId(String userId);
    
    void deleteByUserId(String userId);
}