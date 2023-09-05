package com.application.nothing.controller;

import com.application.nothing.model.CartItem;
import com.application.nothing.service.CartItemService;
import com.application.nothing.exception.CartItemNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get All Cart Items", description = "Fetches a list of all items present in the cart.")
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    // Fetch a single cart item by ID
    @Operation(summary = "Get Cart Item by ID", description = "Fetches the details of a specific cart item identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Cart item fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        return cartItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new cart item
    @Operation(summary = "Create Cart Item", description = "Creates a new cart item with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Cart item created successfully")
    })
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartItemService.save(cartItem));
    }

    // Update a cart item by ID
    @Operation(summary = "Update Cart Item", description = "Updates the details of an existing cart item identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
        if (!cartItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartItem.setCartItemId(id);
        return ResponseEntity.ok(cartItemService.save(cartItem));
    }

    // Delete a cart item by ID
    @Operation(summary = "Delete Cart Item", description = "Deletes a cart item identified by its ID from the cart.", responses = {
            @ApiResponse(responseCode = "204", description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (!cartItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cartItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
