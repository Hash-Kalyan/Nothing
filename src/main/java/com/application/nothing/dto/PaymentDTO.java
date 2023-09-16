
package com.application.nothing.dto;

import com.application.nothing.model.Payment.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDTO {

    private Long paymentId;
    private Long userId;
    private Long orderId;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String transactionId;
    private String paymentGatewayName;
    private String paymentGatewayTransactionId;
    private String bankName;
    private String UPIId;
    private String currency = "INR";
    private LocalDate createdDate;
    private LocalDate lastUpdatedDate;

    // Getters, Setters, Constructors
}

