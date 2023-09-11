package com.application.nothing.dto;

import com.application.nothing.model.CartItem;
import java.util.List;

public class UpdateShoppingCartRequest {

    private Long cartId;
    private List<CartItem> items;
    // other fields that can be updated, for example:
    // private String newField1;
    // private String newField2;

    // getters and setters

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    // getters and setters for other fields

    // public String getNewField1() {
    //     return newField1;
    // }

    // public void setNewField1(String newField1) {
    //     this.newField1 = newField1;
    // }

    // public String getNewField2() {
    //     return newField2;
    // }

    // public void setNewField2(String newField2) {
    //     this.newField2 = newField2;
    // }
}

