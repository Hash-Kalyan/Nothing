package com.application.nothing.model;

import jakarta.persistence.*;
import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_status", columnDefinition = "VARCHAR(255) DEFAULT 'Pending'", nullable = false)
    private String paymentStatus;

    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private LocalDate paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    // Getters, Setters, Constructors, equals, hashCode, and toString methods
}


