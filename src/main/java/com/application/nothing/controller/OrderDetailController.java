package com.application.nothing.controller;

import com.application.nothing.model.OrderDetail;
import com.application.nothing.service.OrderDetailService;
import com.application.nothing.exception.OrderDetailNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    // Fetch all order details
    @Operation(summary = "Get All Order Details", description = "Fetches a list of details for all orders placed in the system.")
    @GetMapping
    public ResponseEntity<List<OrderDetail>> getAllOrderDetails() {
        return ResponseEntity.ok(orderDetailService.findAll());
    }

    // Fetch order details by ID
    @Operation(summary = "Get Order Details by ID", description = "Fetches the detailed information of a specific order identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Order details fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Order details not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailsById(@PathVariable Long id) {
        return orderDetailService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new order details
    @Operation(summary = "Create Order Details", description = "Creates a new detailed record for an order with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Order details created successfully")
    })
    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetails(@RequestBody OrderDetail orderDetails) {
        return ResponseEntity.ok(orderDetailService.save(orderDetails));
    }

    // Update order details by ID
    @Operation(summary = "Update Order Details", description = "Updates the detailed information of an existing order identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Order details updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order details not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetail orderDetails) {
        orderDetails.setOrderDetailId(id);
        return ResponseEntity.ok(orderDetailService.save(orderDetails));
    }

    // Delete order details by ID
    @Operation(summary = "Delete Order Details", description = "Deletes the detailed record of an order identified by its ID from the database.", responses = {
            @ApiResponse(responseCode = "204", description = "Order details deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Order details not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        orderDetailService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}