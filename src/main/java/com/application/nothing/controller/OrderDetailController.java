package com.application.nothing.controller;

import com.application.nothing.dto.OrderDetailDTO;
import com.application.nothing.exception.OrderDetailNotFoundException;
import com.application.nothing.service.OrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        logger.info("Fetching all order details");
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        logger.info("Fetching order detail with ID: {}", id);
        return orderDetailService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderDetailNotFoundException("Order detail not found"));
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        logger.info("Creating new order detail");
        return ResponseEntity.ok(orderDetailService.save(orderDetailDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDTO orderDetailDTO) {
        logger.info("Updating order detail with ID: {}", id);
        orderDetailDTO.setOrderDetailId(id);
        return ResponseEntity.ok(orderDetailService.save(orderDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        logger.info("Deleting order detail with ID: {}", id);
        orderDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




//package com.application.nothing.controller;
//
//import com.application.nothing.dto.OrderDetailDTO;
//import com.application.nothing.service.OrderDetailService;
//import com.application.nothing.exception.OrderDetailNotFoundException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/order-details")
//public class OrderDetailController {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
//
//    @Autowired
//    private OrderDetailService orderDetailService;
//
//    @GetMapping
//    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
//        logger.info("Fetching all order details");
//        return ResponseEntity.ok(orderDetailService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
//        logger.info("Fetching order detail with ID: {}", id);
//        return orderDetailService.findById(id)
//                .map(orderDetailDTO -> {
//                    logger.info("Order detail with ID: {} found", id);
//                    return ResponseEntity.ok(orderDetailDTO);
//                })
//                .orElseThrow(() -> new OrderDetailNotFoundException("Order detail not found with ID: " + id));
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
//        logger.info("Attempting to create a new order detail");
//
//        // Check if an OrderDetail with the same Order ID and Product ID already exists
//        Optional<OrderDetailDTO> existingOrderDetail = orderDetailService.findByOrderIdAndProductId(orderDetailDTO.getOrderId(), orderDetailDTO.getProductId());
//        if (existingOrderDetail.isPresent()) {
//            logger.warn("An order detail with the same Order ID and Product ID already exists");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("An order detail with the same Order ID and Product ID already exists. Consider updating the quantity instead.");
//        }
//
//        OrderDetailDTO createdOrderDetail = orderDetailService.save(orderDetailDTO);
//        logger.info("Successfully created a new order detail with ID: {}", createdOrderDetail.getOrderDetailId());
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDetail);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateOrderDetail(@PathVariable Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
//        logger.info("Attempting to update order detail with ID: {}", id);
//
//        // Check if the OrderDetail exists
//        if (!orderDetailService.existsById(id)) {
//            logger.warn("Order detail with ID: {} not found", id);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order detail not found with ID: " + id);
//        }
//
//        orderDetailDTO.setOrderDetailId(id);
//        OrderDetailDTO updatedOrderDetail = orderDetailService.save(orderDetailDTO);
//        logger.info("Successfully updated order detail with ID: {}", id);
//        return ResponseEntity.ok(updatedOrderDetail);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
//        logger.info("Deleting order detail with ID: {}", id);
//        orderDetailService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}