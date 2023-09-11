package com.application.nothing.repository;

import com.application.nothing.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    Optional<OrderDetail> findByOrder_orderIdAndProduct_productId(Long orderId, Long productId);

    boolean existsById(Long id);

    // ... (other methods)
}


