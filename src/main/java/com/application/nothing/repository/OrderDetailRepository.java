package com.application.nothing.repository;

import com.application.nothing.model.OrderDetail;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    Optional<OrderDetail> findByOrder_orderIdAndProduct_productId(@NonNull Long orderId, @NonNull Long productId);

    boolean existsById(@NonNull Long id);

    // ... (other methods)
}


