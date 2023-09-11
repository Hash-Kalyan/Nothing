package com.application.nothing.controller;

import com.application.nothing.exception.OrderNotFoundException;
import com.application.nothing.dto.OrderDTO;
import com.application.nothing.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.findAll();
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found");
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Optional<OrderDTO> orderDTO = orderService.findById(id);
        return orderDTO.map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDTO> ordersByUser = orderService.findOrdersByUserId(userId);
        if (ordersByUser == null || ordersByUser.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user with ID " + userId);
        }
        return ResponseEntity.ok(ordersByUser);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        if (orderDTO.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Total price must be greater than zero");
        }
        OrderDTO createdOrder = orderService.save(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        if (!orderService.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderDTO.setOrderId(id);
        OrderDTO updatedOrder = orderService.save(orderDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.existsById(id)) {
            throw new OrderNotFoundException("Order with ID " + id + " not found");
        }
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




//package com.application.nothing.controller;
//
//import com.application.nothing.model.Order;
//import com.application.nothing.service.OrderService;
//import com.application.nothing.exception.OrderNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
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
//    public ResponseEntity<List<Order>> getAllOrders() {
//        return ResponseEntity.ok(orderService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
//        return orderService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<Optional<Order>> getOrdersByUser(@PathVariable Long userId) {
//        return ResponseEntity.ok(orderService.findById(userId));
//    }
//
//    @PostMapping
//    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//        return ResponseEntity.ok(orderService.save(order));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
//        order.setOrderId(id);
//        return ResponseEntity.ok(orderService.save(order));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        orderService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
