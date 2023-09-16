package com.application.nothing.repository;

import com.application.nothing.model.CartItem;
import com.application.nothing.model.Product;
import com.application.nothing.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);

    Optional<CartItem> findByShoppingCart_cartIdAndProduct_ProductId(Long shoppingCartId, Long productId);

//    List<CartItem> findByShoppingCartId(Long shoppingCartId);

    // Custom queries can be added here.
}

