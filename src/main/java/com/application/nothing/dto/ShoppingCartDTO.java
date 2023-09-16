package com.application.nothing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private Long cartId;
    private Long userId;
    private List<CartItemDTO> items;
    private LocalDateTime createdDate;
    private String status;
    private Double cartTotal;
}


