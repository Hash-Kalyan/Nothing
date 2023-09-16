package com.application.nothing.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ShoppingCart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    private String status;

    @Column(name = "cart_total")
    private BigDecimal cartTotal;

    private BigDecimal totalAmount;

    private boolean checkedOut;

    public void addItem(CartItem item) {
        items.add(item);
        item.setShoppingCart(this);
        updateCartTotal();
    }
    public void addCartItem(CartItem cartItem) {
        items.add(cartItem);
        cartItem.setShoppingCart(this);
        updateTotalAmount();
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setShoppingCart(null);
        updateCartTotal();
    }
    public void removeCartItem(CartItem cartItem) {
        items.remove(cartItem);
        cartItem.setShoppingCart(null);
        updateTotalAmount();
    }

//    private void updateCartTotal() {
//        this.cartTotal = items.stream()
//                .mapToDouble(item -> item.getPrice() * item.getQuantity())
//                .sum();
//    }
    private void updateCartTotal() {
        this.cartTotal = items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private void updateTotalAmount() {
        this.totalAmount = items.stream()
            .map(CartItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
        this.status = "NEW";
    }





        // Other fields...





        public void checkout() {
            this.checkedOut = true;
            // Additional checkout logic can be added here
        }



    }


