package com.application.nothing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private Long cartItemId;
    private Long shoppingCartId;
    private Long productId;
    private Integer quantity;
    private String additionalNotes;
}