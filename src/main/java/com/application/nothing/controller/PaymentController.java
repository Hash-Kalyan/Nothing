package com.application.nothing.controller;

import com.application.nothing.model.Payment;
import com.application.nothing.service.PaymentService;
import com.application.nothing.exception.PaymentNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    // Fetch all payments
    @Operation(summary = "Get All Payments", description = "Fetches a list of all payment records in the system.")
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.findAll());
    }

    // Fetch a single payment by ID
    @Operation(summary = "Get Payment by ID", description = "Fetches the details of a specific payment identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Payment fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new payment
    @Operation(summary = "Create Payment", description = "Creates a new payment record with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Payment created successfully")
    })
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.save(payment));
    }

    // Update a payment by ID
    @Operation(summary = "Update Payment", description = "Updates the details of an existing payment identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setPaymentId(id);
        return ResponseEntity.ok(paymentService.save(payment));
    }

    // Delete a payment by ID
    @Operation(summary = "Delete Payment", description = "Deletes a payment record identified by its ID from the database.", responses = {
            @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}