package com.foodapp.cart.repository;

import com.foodapp.cart.entity.CartSharing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartSharingRepository extends JpaRepository<CartSharing, Long> {
    
    Optional<CartSharing> findByShareToken(String shareToken);
    
    List<CartSharing> findByCartId(Long cartId);
    
    List<CartSharing> findByOwnerUserId(String ownerUserId);
    
    List<CartSharing> findBySharedWithUserId(String sharedWithUserId);
    
    @Query("SELECT cs FROM CartSharing cs WHERE cs.cartId = :cartId AND cs.isActive = true")
    List<CartSharing> findActiveSharesByCartId(@Param("cartId") Long cartId);
    
    @Query("SELECT cs FROM CartSharing cs WHERE cs.shareToken = :token AND cs.isActive = true AND (cs.expiresAt IS NULL OR cs.expiresAt > :now)")
    Optional<CartSharing> findValidShareByToken(@Param("token") String token, @Param("now") LocalDateTime now);
    
    @Query("SELECT cs FROM CartSharing cs WHERE cs.ownerUserId = :userId AND cs.isActive = true")
    List<CartSharing> findActiveSharesByOwner(@Param("userId") String userId);
    
    @Query("SELECT cs FROM CartSharing cs WHERE cs.sharedWithUserId = :userId AND cs.isActive = true")
    List<CartSharing> findActiveSharesWithUser(@Param("userId") String userId);
    
    @Query("SELECT cs FROM CartSharing cs WHERE cs.expiresAt < :now AND cs.isActive = true")
    List<CartSharing> findExpiredShares(@Param("now") LocalDateTime now);
    
    boolean existsByCartIdAndSharedWithUserId(Long cartId, String sharedWithUserId);
}