package com.application.nothing.repository;

import com.application.nothing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name = :name")

    List<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    List<Product> findAllByCategoryId(Long categoryId);

    // Additional custom queries can be added here.


    List<Product> findBypriceBetween(Float minPrice, Float maxPrice);

    List<Product> findBycreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Product> findByCategory_categoryId(Long categoryId);

    boolean existsByName(String name);
}



//package com.application.nothing.repository;
//
//import com.application.nothing.model.Category;
//import com.application.nothing.model.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface ProductRepository extends JpaRepository<Product, Long> {
//    Product findByname(String name);
//
//    List<Product> findAllByCategory_CategoryId(Long categoryId);
//
//    // Custom queries can be added here.
//}
//
