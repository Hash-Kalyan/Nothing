package com.application.nothing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "payment_gateway_name")
    private String paymentGatewayName;

    @Column(name = "payment_gateway_transaction_id")
    private String paymentGatewayTransactionId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "upi_id")
    private String UPIId;

    @Column(name = "currency", nullable = false, columnDefinition = "VARCHAR(3) DEFAULT 'INR'")
    private String currency;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "last_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    // Other fields, Getters, Setters, Constructors, equals, hashCode, and toString methods

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED
    }
}




//package com.application.nothing.model;
//
//import jakarta.persistence.*;
//import java.time.LocalDate;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "Payments")
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id")
//    private Long paymentId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @Column(name = "payment_status", columnDefinition = "VARCHAR(255) DEFAULT 'Pending'", nullable = false)
//    private String paymentStatus;
//
//    @Column(name = "payment_date")
//    @Temporal(TemporalType.DATE)
//    private LocalDate paymentDate;
//
//    @Column(name = "payment_method", nullable = false)
//    private String paymentMethod;
//
//    @Column(name = "transaction_id", unique = true, nullable = false)
//    private String transactionId;
//
//    // Getters, Setters, Constructors, equals, hashCode, and toString methods
//}


