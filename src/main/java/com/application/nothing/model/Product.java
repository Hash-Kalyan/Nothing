
package com.application.nothing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 1, max = 100, message = "Product name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Product description cannot be null")
    @Size(min = 1, max = 500, message = "Product description must be between 1 and 500 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Product price cannot be null")
    @Column(nullable = false)
//    private Double price;
    private BigDecimal price;

    @NotNull(message = "Stock quantity cannot be null")
    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(System.currentTimeMillis());

    // ... (Constructors and other methods)
}




