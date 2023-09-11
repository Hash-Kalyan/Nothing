package com.application.nothing.controller;

import com.application.nothing.exception.ShoppingCartNotFoundException;
import com.application.nothing.dto.CreateCartItemRequest;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.dto.RemoveCartItemRequest;
import com.application.nothing.dto.UpdateShoppingCartRequest;
import com.application.nothing.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/create")
//    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
//        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
//        return ResponseEntity.ok(createdShoppingCart);
//    }

    public ResponseEntity<ShoppingCart> createShoppingCart(@RequestParam Long userId, @RequestBody ShoppingCart shoppingCart) {

        shoppingCartService.checkIfCartExists(userId);

        ShoppingCart createdShoppingCart = shoppingCartService.createNewShoppingCart(userId, shoppingCart);
        return new ResponseEntity<>(createdShoppingCart, HttpStatus.CREATED);

    }

//    @PostMapping("/add-item")
//    public ResponseEntity<ShoppingCart> addItemToCart(@RequestBody CreateCartItemRequest createCartItemRequest) {
//        ShoppingCart updatedShoppingCart = shoppingCartService.addItemToCart(createCartItemRequest);
//        return ResponseEntity.ok(updatedShoppingCart);
//    }

    @PostMapping("/add-item")
    public ResponseEntity<?> addItemToCart(@RequestBody CreateCartItemRequest createCartItemRequest) {
        if (createCartItemRequest == null || createCartItemRequest.getUserId() == null || createCartItemRequest.getItemId() == null) {
            // Log the error (consider using a logging framework like Logback or Log4j)
            // logger.error("Invalid request: createCartItemRequest or userId or itemId is null");
            return ResponseEntity.badRequest().body("Invalid request: createCartItemRequest or userId or itemId is null");
        }

        try {
            ShoppingCart updatedShoppingCart = shoppingCartService.addItemToCart(createCartItemRequest);
            // Log the success
            // logger.info("Item added to cart successfully: " + updatedShoppingCart);
            return ResponseEntity.ok(updatedShoppingCart);
        } catch (Exception e) {
            // Log the error
            // logger.error("Error adding item to cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding item to cart: " + e.getMessage());
        }
    }


//    @PostMapping("/remove-item")
//    public ResponseEntity<ShoppingCart> removeItemFromCart(@RequestBody CreateCartItemRequest createCartItemRequest) {
//        ShoppingCart updatedShoppingCart = shoppingCartService.removeItemFromCart(createCartItemRequest);
//        return ResponseEntity.ok(updatedShoppingCart);
//    }

    @PostMapping("/remove-item")
    public ResponseEntity<?> removeItemFromCart(@RequestBody RemoveCartItemRequest removeCartItemRequest) {
        if (removeCartItemRequest == null || removeCartItemRequest.getUserId() == null || removeCartItemRequest.getItemId() == null) {
            // Log the error (consider using a logging framework like Logback or Log4j)
            // logger.error("Invalid request: removeCartItemRequest or userId or itemId is null");
            return ResponseEntity.badRequest().body("Invalid request: removeCartItemRequest or userId or itemId is null");
        }

        try {
            ShoppingCart updatedShoppingCart = shoppingCartService.removeItemFromCart(removeCartItemRequest);
            // Log the success
            // logger.info("Item removed from cart successfully: " + updatedShoppingCart);
            return ResponseEntity.ok(updatedShoppingCart);
        } catch (Exception e) {
            // Log the error
            // logger.error("Error removing item from cart", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing item from cart: " + e.getMessage());
        }
    }


    @GetMapping("/{cartId}")
    public ResponseEntity<Optional<ShoppingCart>> getShoppingCart(@PathVariable Long cartId) {
        try {
            Optional<ShoppingCart> shoppingCart = shoppingCartService.getShoppingCart(cartId);
            return ResponseEntity.ok(shoppingCart);
        } catch (ShoppingCartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllShoppingCarts() {
        try {
            List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();
            if (shoppingCarts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No shopping carts found");
            }
            return ResponseEntity.ok(shoppingCarts);
        } catch (Exception e) {
            // Here, you can log the exception message to your log file
            // logger.error("Error fetching all shopping carts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching all shopping carts: " + e.getMessage());
        }
    }

    @PutMapping("/update-cart")
    public ResponseEntity<String> updateShoppingCart(@RequestBody UpdateShoppingCartRequest updateShoppingCartRequest) {
        return shoppingCartService.updateShoppingCart(updateShoppingCartRequest);
    }


}
