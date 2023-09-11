package com.application.nothing.dto;

import lombok.Getter;

@Getter
public class CreateCartItemRequest {

    // Getters and setters
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Long userId;
    private Long itemId;

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUserId(Long userId) {this.userId=userId;}

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

}
