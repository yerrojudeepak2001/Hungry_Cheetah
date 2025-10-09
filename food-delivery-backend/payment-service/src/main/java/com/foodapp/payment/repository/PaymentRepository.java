package com.foodapp.payment.repository;

import com.foodapp.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);
    Optional<Payment> findByOrderId(Long orderId);
    List<Payment> findByPaymentStatus(String status);
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByPaymentMethod(String method);
}