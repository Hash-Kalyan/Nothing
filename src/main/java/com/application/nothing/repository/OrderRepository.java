package com.application.nothing.repository;

import com.application.nothing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom queries can be added here.
}

