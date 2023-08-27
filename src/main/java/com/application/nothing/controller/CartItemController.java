package com.application.nothing.controller;

import com.application.nothing.model.CartItem;
import com.application.nothing.service.CartItemService;
import com.application.nothing.exception.CartItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);

    @Autowired
    private CartItemService cartItemService;

    // Fetch all cart items
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    // Fetch a single cart item by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        return cartItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new cart item
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartItemService.save(cartItem));
    }

    // Update a cart item by ID
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        if (!cartItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartItem.setCartItemId(id);
        return ResponseEntity.ok(cartItemService.save(cartItem));
    }

    // Delete a cart item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (!cartItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
