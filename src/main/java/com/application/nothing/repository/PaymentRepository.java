package com.application.nothing.repository;

import com.application.nothing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom queries can be added here.
}

