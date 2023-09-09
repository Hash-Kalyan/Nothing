package com.application.nothing.repository;

import com.application.nothing.model.Category;
import com.application.nothing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByname(String name);

    List<Product> findAllByCategory_CategoryId(Long categoryId);

    // Custom queries can be added here.
}

