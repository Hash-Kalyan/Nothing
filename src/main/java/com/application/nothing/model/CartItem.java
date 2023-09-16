package com.application.nothing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
//    private Double price;
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

//    public BigDecimal getTotalPrice() {
//        return this.product.getPrice().multiply(new BigDecimal(this.quantity));
//    }

    public BigDecimal getTotalPrice() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    // Getters, Setters, Constructors, equals, hashCode, and toString methods
    // Constructors



}

