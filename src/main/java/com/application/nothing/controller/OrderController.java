package com.application.nothing.controller;

import com.application.nothing.model.Order;
import com.application.nothing.service.OrderService;
import com.application.nothing.exception.OrderNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    // Fetch all orders
    @Operation(summary = "Get All Orders", description = "Fetches a list of all orders placed in the system.")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    // Fetch a single order by ID
    @Operation(summary = "Get Order by ID", description = "Fetches the details of a specific order identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Order fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch orders by user ID
    @Operation(summary = "Get Orders by User ID", description = "Fetches a list of all orders placed by a specific user, identified by their user ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Orders fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Orders not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.findById(userId));
    }

    // Create a new order
    @Operation(summary = "Create Order", description = "Creates a new order with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Order created successfully")
    })
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    // Update an order by ID
    @Operation(summary = "Update Order", description = "Updates the details of an existing order identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setOrderId(id);
        return ResponseEntity.ok(orderService.save(order));
    }

    // Delete an order by ID
    @Operation(summary = "Delete Order", description = "Deletes an order identified by its ID from the database.", responses = {
            @ApiResponse(responseCode = "204", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}