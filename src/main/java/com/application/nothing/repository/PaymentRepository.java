package com.application.nothing.repository;

import com.application.nothing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom queries can be added here.
}

