package com.application.nothing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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

   // @Column(name = "name", nullable = false)
   @Column(nullable = false)
    private String name;
    //@Column(name = "description", nullable = false)
    @Column(nullable = false)
    private String description;
    //@Column(name = "price", nullable = false)
    @Column(nullable = false)
    private Double price;

    //@Column(name = "stock_quantity", nullable = false)
    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date(System.currentTimeMillis());

    // Getters, Setters, Constructors, equals, hashCode, and toString methods
// Constructors

}



