package com.application.nothing.repository;

import com.application.nothing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom queries can be added here.
}

