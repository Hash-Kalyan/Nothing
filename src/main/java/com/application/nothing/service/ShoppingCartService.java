package com.application.nothing.service;

import com.application.nothing.exception.*;
import com.application.nothing.model.User;
import com.application.nothing.dto.CreateCartItemRequest;
import com.application.nothing.model.CartItem;
import com.application.nothing.model.Product;
import com.application.nothing.model.ShoppingCart;
import com.application.nothing.dto.RemoveCartItemRequest;
import com.application.nothing.dto.UpdateShoppingCartRequest;
import com.application.nothing.repository.CartItemRepository;
import com.application.nothing.repository.ProductRepository;
import com.application.nothing.repository.ShoppingCartRepository;
import com.application.nothing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.application.nothing.exception.ShoppingCartNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
//        this.shoppingCartRepository = shoppingCartRepository;
//    }
    @Transactional
    public void checkIfCartExists(Long userId) {
        if (shoppingCartRepository.existsByuser_userId(userId)) {
            throw new UserAlreadyExistsException("A shopping cart already exists for user ID: " + userId);
        }
    }
    @Transactional
    public ShoppingCart createNewShoppingCart(Long userId, ShoppingCart shoppingCart) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        shoppingCart.setUser(user);

        // ... set other properties on shoppingCart

        shoppingCart.setCreatedDate(LocalDateTime.now());
        shoppingCart.setStatus("New");

        // Add other properties as needed based on your ShoppingCart entity definition

        // Now save the shopping cart

        return shoppingCartRepository.save(shoppingCart);

    }
    @Transactional
    public ShoppingCart addItemToCart(CreateCartItemRequest createCartItemRequest) {
        if(createCartItemRequest.getCartId() == null) {
            throw new IllegalArgumentException("Cart ID must not be null");
        }
        ShoppingCart shoppingCart = shoppingCartRepository.findById(createCartItemRequest.getCartId())
                .orElseThrow(() -> new CartItemNotFoundException("Cart not found"));

        Product product = productRepository.findById(createCartItemRequest.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setProduct(product);
        cartItem.setQuantity(createCartItemRequest.getQuantity());

        cartItemRepository.save(cartItem);
        return shoppingCartRepository.save(shoppingCart);
    }

     // ... (rest of your method)
     @Transactional
     public ShoppingCart removeItemFromCart(RemoveCartItemRequest removeCartItemRequest) {
         ShoppingCart shoppingCart = shoppingCartRepository.findById(removeCartItemRequest.getCartId())
                 .orElseThrow(() -> new CartItemNotFoundException("Cart not found"));

         Product product = productRepository.findById(removeCartItemRequest.getProductId())
                 .orElseThrow(() -> new ProductNotFoundException("Product not found"));

         CartItem cartItem = cartItemRepository.findByShoppingCartAndProduct(shoppingCart, product)
                 .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));

         cartItemRepository.delete(cartItem);
         return shoppingCartRepository.save(shoppingCart);
     }
//    @Transactional
//    public ShoppingCart removeItemFromCart(RemoveCartItemRequest removeCartItemRequest) {
//        // Step 1: Fetch the user's cart using the userId
//        ShoppingCart cart = shoppingCartRepository.findByUser_userId(removeCartItemRequest.getUserId())
//                .orElseThrow(() -> new ShoppingCartNotFoundException("Cart not found for the given user ID"));
//
//        // Step 2: Find the item in the cart using the itemId
//        CartItem itemToRemove = cart.getItems().stream()
//                .filter(item -> item.getCartItemId().equals(removeCartItemRequest.getItemId()))
//                .findFirst()
//                .orElseThrow(() -> new CartItemNotFoundException("Item not found in cart"));
//
//        // Step 3: Remove the item from the cart
//        cart.getItems().remove(itemToRemove);
//
//        // Step 4: Save the updated cart back to the database
//        shoppingCartRepository.save(cart);
//
//        return cart;
//    }

    @Transactional(readOnly = true)
    public List<ShoppingCart> findAll() {return shoppingCartRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ShoppingCart> getShoppingCart(Long cartId) {

        return Optional.ofNullable(shoppingCartRepository.findById(cartId).orElseThrow(() ->
                new ShoppingCartNotFoundException("NO ShoppingCart PRESENT WITH ID = " + cartId)));
    }
//    public ShoppingCart getShoppingCart(Long cartId) {
//        return shoppingCartRepository.findById(cartId)
//                .orElseThrow(() -> new ShoppingCartNotFoundException("Cart not found"));
//    }

//    @Transactional
//    public ResponseEntity<String> updateShoppingCart(ShoppingCart shoppingCart) {
//        try {
//            shoppingCartRepository.save(shoppingCart);
//            return ResponseEntity.ok("ShoppingCart updated successfully.");
//        } catch (DataIntegrityViolationException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Shopping Cart.");
//        }
//    }

    @Transactional
    public ResponseEntity<String> updateShoppingCart(UpdateShoppingCartRequest updateShoppingCartRequest) {
        try {
            ShoppingCart shoppingCart = shoppingCartRepository.findById(updateShoppingCartRequest.getCartId())
                    .orElseThrow(() -> new ShoppingCartNotFoundException("Shopping cart not found"));

            shoppingCart.setItems(updateShoppingCartRequest.getItems());
            // Update other fields as necessary

            shoppingCartRepository.save(shoppingCart);
            return ResponseEntity.ok("ShoppingCart updated successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Shopping Cart.");
        }
    }


    @Transactional
    public ResponseEntity<String> deleteById(Long id) {
        shoppingCartRepository.findById(id).orElseThrow(() ->
                new ShoppingCartNotFoundException("NO ShoppingCart PRESENT WITH ID = " + id));;
        try {
            shoppingCartRepository.deleteById(id);
            return ResponseEntity.ok("ShoppingCart deleted successfully.");

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting ShoppingCart.!!");
            // Handle the unique constraint violation, e.g., return an error message to the user

        }

    }
}
