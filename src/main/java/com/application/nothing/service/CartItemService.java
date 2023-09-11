package com.application.nothing.service;

import com.application.nothing.dto.CartItemDTO;
import com.application.nothing.repository.CartItemRepository;
import com.application.nothing.model.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findAll() {
        logger.info("Fetching all cart items");
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> findById(Long id) {
        logger.info("Fetching cart item by ID: {}", id);
        return cartItemRepository.findById(id);
    }

    public CartItem save(CartItem cartItem) {
        logger.info("Saving cart item: {}", cartItem);
        return cartItemRepository.save(cartItem);
    }

    public void deleteById(Long id) {
        logger.info("Deleting cart item by ID: {}", id);
        cartItemRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        logger.info("Checking if cart item exists with ID: {}", id);
        return cartItemRepository.existsById(id);
    }

//    public Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product) {
//        return cartItemRepository.findByShoppingCartAndProduct(shoppingCart, product);
//    }
    public Optional<CartItem> findByShoppingCartAndProduct(Long shoppingCartId, Long productId) {
        // Implement the logic to find a cart item by shoppingCartId and productId
        // You can use your repository's method here
        return cartItemRepository.findByShoppingCart_cartIdAndProduct_ProductId(shoppingCartId, productId);
    }


    // Convert CartItem entities to CartItemDTOs
    public List<CartItemDTO> convertCartItemsToDTOs(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> new CartItemDTO(
                        cartItem.getCartItemId(),
                        cartItem.getShoppingCart().getCartId(),
                        cartItem.getProduct().getProductId(), // Assuming you have a Product entity associated with CartItem
                        cartItem.getQuantity(),
                        ""
                ))
                .collect(Collectors.toList());
    }

    // Convert CartItemDTO to CartItem entity
    public CartItem convertDTOToCartItem(CartItemDTO cartItemDTO) {
        CartItem cartItem = new CartItem();
        // Map fields from CartItemDTO to CartItem
        cartItem.setCartItemId(cartItemDTO.getCartItemId());
        // Set other fields as needed
        // ...
        return cartItem;
    }
}


//package com.application.nothing.service;
//
//import com.application.nothing.repository.CartItemRepository;
//import com.application.nothing.model.CartItem;
//import com.application.nothing.exception.CartItemNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartItemService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CartItemService.class);
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    public List<CartItem> findAll() {
//        return cartItemRepository.findAll();
//    }
//
//    public Optional<CartItem> findById(Long id) {
//        return cartItemRepository.findById(id);
//    }
//
//    public CartItem save(CartItem cartItem) {
//        return cartItemRepository.save(cartItem);
//    }
//
//    public void deleteById(Long id) {
//        cartItemRepository.deleteById(id);
//    }
//
//    public boolean existsById(Long id) {return cartItemRepository.existsById(id); }
//}
