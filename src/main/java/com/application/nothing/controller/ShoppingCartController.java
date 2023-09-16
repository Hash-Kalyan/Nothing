package com.application.nothing.controller;

import com.application.nothing.dto.CartItemDTO;
import com.application.nothing.dto.ShoppingCartDTO;
import com.application.nothing.dto.UserDTO;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import com.application.nothing.exception.UserNotFoundException;
import com.application.nothing.service.ShoppingCartService;
import com.application.nothing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestParam Long userId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
        // Check if the user exists
        UserDTO user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        // Set the user to the shopping cart DTO
        shoppingCartDTO.setUserId(userId);

        // Call the service to create a new shopping cart
        ShoppingCartDTO createdCart = shoppingCartService.createShoppingCart(shoppingCartDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Long cartId) {
        // Call the service to fetch the shopping cart
        ShoppingCartDTO cart = shoppingCartService.findById(cartId).orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@PathVariable Long cartId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
        // Check if the cart exists
        ShoppingCartDTO existingCart = shoppingCartService.findById(cartId).orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        // Update the cart with the new details
        shoppingCartDTO.setCartId(cartId);
        ShoppingCartDTO updatedCart = shoppingCartService.updateShoppingCart(shoppingCartDTO);

        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<ShoppingCartDTO> addItemToCart(
            @PathVariable Long cartId,
            @RequestBody CartItemDTO cartItemDTO
    ) {
        // Check if the cart exists
        ShoppingCartDTO existingCart = shoppingCartService.findById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        // Add the item to the cart
        ShoppingCartDTO updatedCart = shoppingCartService.addItemToCart(cartId, cartItemDTO);
        return ResponseEntity.ok(updatedCart);
    }
    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        // Check if the cart exists
        ShoppingCartDTO existingCart = shoppingCartService.findById(cartId).orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        // Remove the item from the cart
        shoppingCartService.removeItemFromCart(cartId, itemId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cartId}/checkout")
    public ResponseEntity<ShoppingCartDTO> checkoutCart(@PathVariable Long cartId) {
        // Check if the cart exists
        ShoppingCartDTO existingCart = shoppingCartService.findById(cartId).orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

        // Checkout the cart
        ShoppingCartDTO checkedOutCart = shoppingCartService.checkoutCart(cartId);

        return ResponseEntity.ok(checkedOutCart);
    }

    // Exception handlers and other methods will go here
}


//package com.application.nothing.controller;
//
//import com.application.nothing.dto.CreateCartItemRequest;
//import com.application.nothing.dto.RemoveCartItemRequest;
//import com.application.nothing.dto.ShoppingCartDTO;
//import com.application.nothing.dto.UpdateShoppingCartRequest;
//import com.application.nothing.exception.ShoppingCartAlreadyExistsException;
//import com.application.nothing.exception.ShoppingCartNotFoundException;
//import com.application.nothing.service.ShoppingCartService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/shopping-cart")
//public class ShoppingCartController {
//
//    private final ShoppingCartService shoppingCartService;
//
//    public ShoppingCartController(ShoppingCartService shoppingCartService) {
//        this.shoppingCartService = shoppingCartService;
//    }
//
////    @PostMapping("/create")
////    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestParam Long userId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
////        shoppingCartService.checkIfCartExists(userId);
////        ShoppingCartDTO createdShoppingCart = shoppingCartService.createNewShoppingCart(userId, shoppingCartDTO);
////        return new ResponseEntity<>(createdShoppingCart, HttpStatus.CREATED);
////    }
//    @PostMapping("/create")
//    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestParam Long userId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
//        try {
//            shoppingCartService.checkIfCartExists(userId);
//            ShoppingCartDTO createdShoppingCart = shoppingCartService.createNewShoppingCart(userId, shoppingCartDTO);
//            return new ResponseEntity<>(createdShoppingCart, HttpStatus.CREATED);
//        } catch (ShoppingCartAlreadyExistsException e) {
//            // Log the error using a logger
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        } catch (Exception e) {
//            // Log the error using a logger
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//
//    @PostMapping("/add-item")
//    public ResponseEntity<?> addItemToCart(@RequestBody CreateCartItemRequest createCartItemRequest) {
//        if (createCartItemRequest == null || createCartItemRequest.getUserId() == null || createCartItemRequest.getItemId() == null) {
//            return ResponseEntity.badRequest().body("Invalid request: createCartItemRequest or userId or itemId is null");
//        }
//
//        try {
//            ShoppingCartDTO updatedShoppingCart = shoppingCartService.addItemToCart(createCartItemRequest);
//            return ResponseEntity.ok(updatedShoppingCart);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding item to cart: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/remove-item")
//    public ResponseEntity<?> removeItemFromCart(@RequestBody RemoveCartItemRequest removeCartItemRequest) {
//        if (removeCartItemRequest == null || removeCartItemRequest.getUserId() == null || removeCartItemRequest.getItemId() == null) {
//            return ResponseEntity.badRequest().body("Invalid request: removeCartItemRequest or userId or itemId is null");
//        }
//
//        try {
//            ShoppingCartDTO updatedShoppingCart = shoppingCartService.removeItemFromCart(removeCartItemRequest);
//            return ResponseEntity.ok(updatedShoppingCart);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error removing item from cart: " + e.getMessage());
//        }
//    }
//
//    @GetMapping("/{cartId}")
//    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Long cartId) {
//        try {
//            Optional<ShoppingCartDTO> shoppingCartDTOOptional = shoppingCartService.getShoppingCart(cartId);
//            if (shoppingCartDTOOptional.isPresent()) {
//                return ResponseEntity.ok(shoppingCartDTOOptional.get());
//            } else {
//                throw new ShoppingCartNotFoundException("Shopping cart not found with ID: " + cartId);
//            }
//        } catch (ShoppingCartNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//
//    @GetMapping("/all")
//    public ResponseEntity<?> getAllShoppingCarts() {
//        try {
//            List<ShoppingCartDTO> shoppingCarts = shoppingCartService.findAll();
//            if (shoppingCarts.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No shopping carts found");
//            }
//            return ResponseEntity.ok(shoppingCarts);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching all shopping carts: " + e.getMessage());
//        }
//    }
//
//    @PutMapping("/update-cart")
//    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@RequestBody UpdateShoppingCartRequest updateShoppingCartRequest) {
//        ShoppingCartDTO updatedShoppingCartDTO = shoppingCartService.updateShoppingCart(updateShoppingCartRequest);
//        return ResponseEntity.ok(updatedShoppingCartDTO);
//    }
//}

