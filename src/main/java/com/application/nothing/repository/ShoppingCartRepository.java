package com.application.nothing.repository;

import com.application.nothing.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    // Custom queries can be added here.
}

