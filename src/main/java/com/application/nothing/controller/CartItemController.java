package com.application.nothing.controller;

import com.application.nothing.dto.CartItemDTO;
import com.application.nothing.dto.ErrorResponseDTO;
import com.application.nothing.exception.CartItemAlreadyExistsException;
import com.application.nothing.model.CartItem;
import com.application.nothing.service.CartItemService;
import com.application.nothing.exception.CartItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // Fetch all cart items and map them to CartItemDTO
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    // Fetch a single cart item by ID and map it to CartItemDTO
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
        return cartItemService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CartItemNotFoundException("Cart Item not found with ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
        try {
            return ResponseEntity.ok(cartItemService.createCartItem(cartItemDTO));
        } catch (CartItemAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Cart item already exists. Use update to modify quantity."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long id, @RequestBody @Valid CartItemDTO cartItemDTO) {
        try {
            return ResponseEntity.ok(cartItemService.updateCartItem(id, cartItemDTO));
        } catch (CartItemNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO("Cart item not found with ID: " + id));
        }
    }

    // Delete a cart item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (!cartItemService.existsById(id)) {
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}


//@RestController
//@RequestMapping("/api/cart-items")
//public class CartItemController {
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    // Fetch all cart items and map them to CartItemDTO
//    @GetMapping
//    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
//        List<CartItem> cartItems = cartItemService.findAll();
//        List<CartItemDTO> cartItemDTOs = cartItems.stream()
//                .map(cartItem -> new CartItemDTO(
//                        cartItem.getCartItemId(),
//                        cartItem.getShoppingCart().getCartId(),
//                        cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                        cartItem.getQuantity(),
//                        ""
//                ))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(cartItemDTOs);
//    }
//
//    // Fetch a single cart item by ID and map it to CartItemDTO
//    @GetMapping("/{id}")
//    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
//        Optional<CartItem> cartItem = cartItemService.findById(id);
//        return cartItem.map(value -> ResponseEntity.ok(new CartItemDTO(
//                value.getCartItemId(),
//                value.getShoppingCart().getCartId(),
//                value.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                value.getQuantity(),
//                ""
//        ))).orElseThrow(() -> new CartItemNotFoundException("Cart Item not found with ID: " + id));
//    }
//
//    @PostMapping
//    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
//        // Check if a similar cart item already exists based on shoppingCart and product
//        Optional<CartItem> existingCartItem = cartItemService.findByShoppingCartAndProduct(
//                cartItemDTO.getShoppingCartId(),
//                cartItemDTO.getProductId()
//        );
//
//        if (existingCartItem.isPresent()) {
//            // A similar cart item already exists, update its quantity
//            CartItem existingItem = existingCartItem.get();
//            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
//            existingItem = cartItemService.save(existingItem);
//
//            return ResponseEntity.ok(new CartItemDTO(
//                    existingItem.getCartItemId(),
//                    existingItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
//                    existingItem.getProduct().getProductId(),
//                    existingItem.getQuantity(),
//                    "Cart item quantity increased."
//            ));
//        } else {
//            // Cart item doesn't exist, create it
//            CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
//            cartItem = cartItemService.save(cartItem);
//
//            return ResponseEntity.ok(new CartItemDTO(
//                    cartItem.getCartItemId(),
//                    cartItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
//                    cartItem.getProduct().getProductId(),
//                    cartItem.getQuantity(),
//                    "Cart item created."
//            ));
//        }
//    }
//
//
//    // Update a cart item by ID and map the result to CartItemDTO
//    @PutMapping("/{id}")
//    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestBody @Valid CartItemDTO cartItemDTO) {
//        if (!cartItemService.existsById(id)) {
//            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
//        }
//        CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
//        cartItem.setCartItemId(id);
//        cartItem = cartItemService.save(cartItem);
//        return ResponseEntity.ok(new CartItemDTO(
//                cartItem.getCartItemId(),
//                cartItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
//                cartItem.getProduct().getProductId(),
//                cartItem.getQuantity(),
//                "Cart item updated."
//        ));
//    }
//
//    // Delete a cart item by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
//        if (!cartItemService.existsById(id)) {
//            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
//        }
//        cartItemService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
