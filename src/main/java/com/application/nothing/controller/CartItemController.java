package com.application.nothing.controller;

import com.application.nothing.dto.CartItemDTO;
import com.application.nothing.model.CartItem;
import com.application.nothing.service.CartItemService;
import com.application.nothing.exception.CartItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<CartItem> cartItems = cartItemService.findAll();
        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(cartItem -> new CartItemDTO(
                        cartItem.getCartItemId(),
                        cartItem.getShoppingCart().getCartId(),
                        cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
                        cartItem.getQuantity(),
                        ""
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cartItemDTOs);
    }

    // Fetch a single cart item by ID and map it to CartItemDTO
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
        Optional<CartItem> cartItem = cartItemService.findById(id);
        return cartItem.map(value -> ResponseEntity.ok(new CartItemDTO(
                value.getCartItemId(),
                value.getShoppingCart().getCartId(),
                value.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
                value.getQuantity(),
                ""
        ))).orElseThrow(() -> new CartItemNotFoundException("Cart Item not found with ID: " + id));
    }

//    // Create a new cart item from CartItemDTO and map the result to CartItemDTO
//    @PostMapping
//    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
//        CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
//        cartItem = cartItemService.save(cartItem);
//        return ResponseEntity.ok(new CartItemDTO(
//                cartItem.getCartItemId(),
//                cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                cartItem.getQuantity()
//        ));
//    }

//    // Create a new cart item from CartItemDTO and map the result to CartItemDTO
//    @PostMapping
//    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
//        // Check if a similar cart item already exists based on shoppingCart and product
//        Optional<CartItem> existingCartItem = cartItemService.findByShoppingCartAndProduct(
//                cartItemDTO.getShoppingCartId(),
//                cartItemDTO.getProductId()
//        );
//
//        if (existingCartItem.isPresent()) {
//            // A similar cart item already exists, handle accordingly (e.g., update quantity)
//            CartItem existingItem = existingCartItem.get();
//            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
//            existingItem = cartItemService.save(existingItem);
//
//            return ResponseEntity.ok(new CartItemDTO(
//                    existingItem.getCartItemId(),
//                    existingItem.getProduct().getProductId(),
//                    existingItem.getQuantity()
//            ));
//        } else {
//            // Cart item doesn't exist, create it
//            CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
//            cartItem = cartItemService.save(cartItem);
//
//            return ResponseEntity.ok(new CartItemDTO(
//                    cartItem.getCartItemId(),
//                    cartItem.getProduct().getProductId(),
//                    cartItem.getQuantity()
//            ));
//        }
//    }

    // Create a new cart item from CartItemDTO and map the result to CartItemDTO
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
//                    cartItem.getProduct().getProductId(),
//                    cartItem.getQuantity(),
//                    "Cart item created."
//            ));
//        }
//    }

    @PostMapping
    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
        // Check if a similar cart item already exists based on shoppingCart and product
        Optional<CartItem> existingCartItem = cartItemService.findByShoppingCartAndProduct(
                cartItemDTO.getShoppingCartId(),
                cartItemDTO.getProductId()
        );

        if (existingCartItem.isPresent()) {
            // A similar cart item already exists, update its quantity
            CartItem existingItem = existingCartItem.get();
            existingItem.setQuantity(existingItem.getQuantity() + cartItemDTO.getQuantity());
            existingItem = cartItemService.save(existingItem);

            return ResponseEntity.ok(new CartItemDTO(
                    existingItem.getCartItemId(),
                    existingItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
                    existingItem.getProduct().getProductId(),
                    existingItem.getQuantity(),
                    "Cart item quantity increased."
            ));
        } else {
            // Cart item doesn't exist, create it
            CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
            cartItem = cartItemService.save(cartItem);

            return ResponseEntity.ok(new CartItemDTO(
                    cartItem.getCartItemId(),
                    cartItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
                    cartItem.getProduct().getProductId(),
                    cartItem.getQuantity(),
                    "Cart item created."
            ));
        }
    }


    // Update a cart item by ID and map the result to CartItemDTO
    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestBody @Valid CartItemDTO cartItemDTO) {
        if (!cartItemService.existsById(id)) {
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        CartItem cartItem = cartItemService.convertDTOToCartItem(cartItemDTO);
        cartItem.setCartItemId(id);
        cartItem = cartItemService.save(cartItem);
        return ResponseEntity.ok(new CartItemDTO(
                cartItem.getCartItemId(),
                cartItem.getShoppingCart().getCartId(), // Assuming you have a Shopping Cart entity associated with CartItem
                cartItem.getProduct().getProductId(),
                cartItem.getQuantity(),
                "Cart item updated."
        ));
    }

    // Delete a cart item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (!cartItemService.existsById(id)) {
            throw new CartItemNotFoundException("Cart Item not found with ID: " + id);
        }
        cartItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



//package com.application.nothing.controller;
//
//import com.application.nothing.model.dto.CartItemDTO;
//import com.application.nothing.model.CartItem;
//import com.application.nothing.service.CartItemService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
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
//                        cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                        cartItem.getQuantity()
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
//                value.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                value.getQuantity()
//        ))).orElse(ResponseEntity.notFound().build());
//    }
//
//    // Create a new cart item from CartItemDTO and map the result to CartItemDTO
//    @PostMapping
//    public ResponseEntity<CartItemDTO> createCartItem(@RequestBody @Valid CartItemDTO cartItemDTO) {
//        CartItem cartItem = new CartItem();
//        // Map fields from CartItemDTO to CartItem
//        cartItem.setCartItemId(cartItemDTO.getId());
//        // Set other fields as needed
//        // ...
//        cartItem = cartItemService.save(cartItem);
//        return ResponseEntity.ok(new CartItemDTO(
//                cartItem.getCartItemId(),
//                cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                cartItem.getQuantity()
//        ));
//    }
//
//    // Update a cart item by ID and map the result to CartItemDTO
//    @PutMapping("/{id}")
//    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestBody @Valid CartItemDTO cartItemDTO) {
//        if (!cartItemService.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        CartItem cartItem = new CartItem();
//        // Map fields from CartItemDTO to CartItem
//        cartItem.setCartItemId(id);
//        // Set other fields as needed
//        // ...
//        cartItem = cartItemService.save(cartItem);
//        return ResponseEntity.ok(new CartItemDTO(
//                cartItem.getCartItemId(),
//                cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
//                cartItem.getQuantity()
//        ));
//    }
//
//    // Delete a cart item by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
//        if (!cartItemService.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        cartItemService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}




//package com.application.nothing.controller;
//
//import com.application.nothing.model.CartItem;
//import com.application.nothing.repository.CartItemRepository;
//import com.application.nothing.service.CartItemService;
//import com.application.nothing.exception.CartItemNotFoundException;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/cart-items")
//public class CartItemController {
//
//    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    @PostMapping
//    public ResponseEntity<CartItem> createCartItem(@RequestBody @Valid CartItem cartItem) {
//        // Check if a similar cart item already exists
//        Optional<CartItem> existingCartItem = cartItemService.findByShoppingCartAndProduct(cartItem.getShoppingCart(), cartItem.getProduct());
//
//        if (existingCartItem.isPresent()) {
//            // A similar cart item already exists
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(existingCartItem.get());
//        } else {
//            // Cart item doesn't exist, create it
//            return ResponseEntity.ok(cartItemService.save(cartItem));
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CartItem>> getAllCartItems() {
//        List<CartItem> cartItems = cartItemService.findAll();
//
//        if (cartItems.isEmpty()) {
//            // Return 204 No Content for an empty list
//            return ResponseEntity.noContent().build();
//        } else {
//            // Return 200 OK with the list of cart items
//            return ResponseEntity.ok(cartItems);
//        }
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
//        return cartItemService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> {
//                    logger.warn("Cart item not found with ID: {}", id);
//                    return ResponseEntity.notFound().build();
//                });
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody @Valid CartItem cartItem) {
//        return cartItemService.findById(id)
//                .map(existingCartItem -> {
//                    cartItem.setCartItemId(id);
//                    return ResponseEntity.ok(cartItemService.save(cartItem));
//                })
//                .orElseGet(() -> {
//                    logger.warn("Cart item not found with ID: {}", id);
//                    return ResponseEntity.notFound().build();
//                });
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
//        if (!cartItemService.existsById(id)) {
//            logger.warn("Cart item not found with ID: {}", id);
//            return ResponseEntity.notFound().build();
//        }
//        cartItemService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}



//package com.application.nothing.controller;
//
//import com.application.nothing.model.CartItem;
//import com.application.nothing.service.CartItemService;
//import com.application.nothing.exception.CartItemNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cart-items")
//public class CartItemController {
//
//    private static final Logger logger = LoggerFactory.getLogger(CartItemController.class);
//
//    @Autowired
//    private CartItemService cartItemService;
//
//    // Fetch all cart items
//    @GetMapping
//    public ResponseEntity<List<CartItem>> getAllCartItems() {
//        return ResponseEntity.ok(cartItemService.findAll());
//    }
//
//    // Fetch a single cart item by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
//        return cartItemService.findById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Create a new cart item
//    @PostMapping
//    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
//        return ResponseEntity.ok(cartItemService.save(cartItem));
//    }
//
//    // Update a cart item by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem) {
//        if (!cartItemService.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        cartItem.setCartItemId(id);
//        return ResponseEntity.ok(cartItemService.save(cartItem));
//    }
//
//    // Delete a cart item by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
//        if (!cartItemService.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        cartItemService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
