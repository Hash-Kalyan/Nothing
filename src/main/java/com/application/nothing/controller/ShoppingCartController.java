package com.application.nothing.controller;

import com.application.nothing.model.ShoppingCart;
import com.application.nothing.service.ShoppingCartService;
import com.application.nothing.exception.ShoppingCartNotFoundException;
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

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        return ResponseEntity.ok(shoppingCartService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        return shoppingCartService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        return ResponseEntity.ok(shoppingCartService.save(shoppingCart));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setCartId(id);
        return ResponseEntity.ok(shoppingCartService.save(shoppingCart));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
