package com.application.nothing.service;

import com.application.nothing.dto.PaymentDTO;
import com.application.nothing.exception.PaymentAlreadyExistsException;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.model.User;
import com.application.nothing.repository.PaymentRepository;
import com.application.nothing.model.Payment;
import com.application.nothing.exception.PaymentNotFoundException;
import com.application.nothing.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<PaymentDTO> findAll() {
        logger.info("Fetching all payments");
        List<PaymentDTO> payments = paymentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        logger.info("Retrieved {} payments", payments.size());
        return payments;
    }

    public Optional<PaymentDTO> findById(Long id) {
        logger.info("Fetching payment with ID: {}", id);
        Optional<PaymentDTO> paymentDTO = paymentRepository.findById(id)
                .map(this::convertToDto);
        if(paymentDTO.isPresent()) {
            logger.info("Payment with ID: {} found", id);
        } else {
            logger.warn("No payment found with ID: {}", id);
        }
        return paymentDTO;
    }

    public PaymentDTO save(PaymentDTO paymentDTO) {
        if (paymentRepository.existsById(paymentDTO.getPaymentId())) {
            logger.error("Payment creation failed. A payment with ID {} already exists", paymentDTO.getPaymentId());
            throw new PaymentAlreadyExistsException("A payment with this ID already exists");
        }

        logger.info("Creating new payment with details: {}", paymentDTO);
        Payment payment = convertToEntity(paymentDTO);
        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Payment created successfully with ID: {}", savedPayment.getPaymentId());
        return convertToDto(savedPayment);
    }

    public void deleteById(Long id) {
        logger.info("Deleting payment with ID: {}", id);
        if (!paymentRepository.existsById(id)) {
            logger.error("Deletion failed. No payment found with ID: {}", id);
            throw new PaymentNotFoundException("No payment found with this ID");
        }
        paymentRepository.deleteById(id);
        logger.info("Payment with ID: {} deleted successfully", id);
    }

    private Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        User user = userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        payment.setUser(user);
        return payment;
    }

    private PaymentDTO convertToDto(Payment payment) {
        PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
        paymentDTO.setUserId(payment.getUser().getUserId());
        return paymentDTO;
    }

}




//package com.application.nothing.service;
//
//import com.application.nothing.repository.PaymentRepository;
//import com.application.nothing.model.Payment;
//import com.application.nothing.exception.PaymentNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PaymentService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//
//
//    public List<Payment> findAll() {
//        return paymentRepository.findAll();
//    }
//
//    public Optional<Payment> findById(Long id) {
//        return paymentRepository.findById(id);
//    }
//
//    public Payment save(Payment payment) {
//        return paymentRepository.save(payment);
//    }
//
//    public void deleteById(Long id) {
//        paymentRepository.deleteById(id);
//    }
//}
