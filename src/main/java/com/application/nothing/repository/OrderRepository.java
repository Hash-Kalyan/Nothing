package com.application.nothing.repository;

import com.application.nothing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.userId = :userId")
    List<Order> findByUserId(Long userId);

    // Additional custom queries can be added here.
}



//package com.application.nothing.repository;
//
//import com.application.nothing.model.Order;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface OrderRepository extends JpaRepository<Order, Long> {
//
//    List<Order> findByUser_userId(Long userId);
//
//    // Custom queries can be added here.
//}
//
