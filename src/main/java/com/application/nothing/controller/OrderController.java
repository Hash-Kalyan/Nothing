package com.application.nothing.controller;

import com.application.nothing.dto.OrderDTO;
import com.application.nothing.exception.OrderNotFoundException;
import com.application.nothing.service.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

//    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
//
//    @Autowired
//    private OrderService orderService;

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        logger.info("Fetching all orders");
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order with ID: {}", id);
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        logger.info("Creating new order");
        return ResponseEntity.ok(orderService.save(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        logger.info("Updating order with ID: {}", id);
        orderDTO.setOrderId(id);
        return ResponseEntity.ok(orderService.save(orderDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        logger.info("Deleting order with ID: {}", id);
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


//package com.application.nothing.controller;
//
//import com.application.nothing.exception.OrderNotFoundException;
//import com.application.nothing.dto.OrderDTO;
//import com.application.nothing.service.OrderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/orders")
//public class OrderController {
//
//    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
//
//    @Autowired
//    private OrderService orderService;
//
//    @GetMapping
//    public ResponseEntity<List<OrderDTO>> getAllOrders() {
//        List<OrderDTO> orders = orderService.findAll();
//        if (orders.isEmpty()) {
//            throw new OrderNotFoundException("No orders found");
//        }
//        return ResponseEntity.ok(orders);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
//        Optional<OrderDTO> orderDTO = orderService.findById(id);
//        return orderDTO.map(ResponseEntity::ok)
//                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
//        List<OrderDTO> ordersByUser = orderService.findOrdersByUserId(userId);
//        if (ordersByUser == null || ordersByUser.isEmpty()) {
//            throw new OrderNotFoundException("No orders found for user with ID " + userId);
//        }
//        return ResponseEntity.ok(ordersByUser);
//    }
//
//    @PostMapping
//    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
//        if (orderDTO.getTotalPrice() <= 0) {
//            throw new IllegalArgumentException("Total price must be greater than zero");
//        }
//        OrderDTO createdOrder = orderService.save(orderDTO);
//        return ResponseEntity.ok(createdOrder);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
//        if (!orderService.existsById(id)) {
//            throw new OrderNotFoundException("Order with ID " + id + " not found");
//        }
//        orderDTO.setOrderId(id);
//        OrderDTO updatedOrder = orderService.save(orderDTO);
//        return ResponseEntity.ok(updatedOrder);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        if (!orderService.existsById(id)) {
//            throw new OrderNotFoundException("Order with ID " + id + " not found");
//        }
//        orderService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}