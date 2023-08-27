package com.application.nothing.repository;

import com.application.nothing.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Custom queries can be added here.
}

