package com.application.nothing.controller;

import com.application.nothing.model.ShoppingCart;
import com.application.nothing.service.ShoppingCartService;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Fetch all shopping carts
    @Operation(summary = "Get All Shopping Carts", description = "Fetches a list of all shopping carts available in the system.")
    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.findAll());
    }

    // Fetch a single shopping cart by ID
    @Operation(summary = "Get Shopping Cart by ID", description = "Fetches the details of a specific shopping cart identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Shopping cart fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        return shoppingCartService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new shopping cart
    @Operation(summary = "Create Shopping Cart", description = "Creates a new shopping cart with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Shopping cart created successfully")
    })
    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return ResponseEntity.ok(shoppingCartService.save(shoppingCart));
    }

    // Update a shopping cart by ID
    @Operation(summary = "Update Shopping Cart", description = "Updates the details of an existing shopping cart identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Shopping cart updated successfully"),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setCartId(id);
        return ResponseEntity.ok(shoppingCartService.save(shoppingCart));
    }

    // Delete a shopping cart by ID
    @Operation(summary = "Delete Shopping Cart", description = "Deletes a shopping cart identified by its ID from the system.", responses = {
            @ApiResponse(responseCode = "204", description = "Shopping cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

