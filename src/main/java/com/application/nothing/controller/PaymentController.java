package com.application.nothing.controller;

import com.application.nothing.dto.PaymentDTO;
import com.application.nothing.model.Payment;
import com.application.nothing.service.PaymentService;
import com.application.nothing.exception.PaymentNotFoundException;
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

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        logger.info("Fetching all payments");
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        logger.info("Fetching payment with ID: {}", id);
        return paymentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        logger.info("Creating new payment");
        return ResponseEntity.ok(paymentService.save(paymentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        logger.info("Updating payment with ID: {}", id);
        paymentDTO.setPaymentId(id);
        return ResponseEntity.ok(paymentService.save(paymentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        logger.info("Deleting payment with ID: {}", id);
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




//package com.application.nothing.controller;
//
//import com.application.nothing.model.Payment;
//import com.application.nothing.service.PaymentService;
//import com.application.nothing.exception.PaymentNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/payments")
//public class PaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @GetMapping
//    public ResponseEntity<List<Payment>> getAllPayments() {
//        return ResponseEntity.ok(paymentService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
//        return paymentService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
//        return ResponseEntity.ok(paymentService.save(payment));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
//        payment.setPaymentId(id);
//        return ResponseEntity.ok(paymentService.save(payment));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
//        paymentService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
