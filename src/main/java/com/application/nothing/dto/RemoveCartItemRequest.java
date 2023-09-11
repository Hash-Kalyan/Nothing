package com.application.nothing.dto;

import lombok.Getter;
@Getter
public class RemoveCartItemRequest {

    private Long userId;
    private Long itemId;
    private Long cartId;
    private Long productId;

    public void setUserId(Long userId) {this.userId=userId;}

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
