package com.foodapp.payment.repository;

import com.foodapp.payment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByOrderId(Long orderId);
    Optional<PaymentTransaction> findByTransactionId(String transactionId);
    Optional<PaymentTransaction> findByGatewayTransactionId(String gatewayTransactionId);
    List<PaymentTransaction> findByStatus(String status);
    List<PaymentTransaction> findByType(String type);
}