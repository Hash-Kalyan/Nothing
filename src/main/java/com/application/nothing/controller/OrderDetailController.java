package com.application.nothing.controller;

import com.application.nothing.model.OrderDetail;
import com.application.nothing.service.OrderDetailService;
import com.application.nothing.exception.OrderDetailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailsById(@PathVariable Long id) {
        return orderDetailService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetails(@RequestBody OrderDetail orderDetails) {
        return ResponseEntity.ok(orderDetailService.save(orderDetails));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetail orderDetails) {
        orderDetails.setOrderDetailId(id);
        return ResponseEntity.ok(orderDetailService.save(orderDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        orderDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
