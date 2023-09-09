package com.application.nothing.model.dto;

import com.application.nothing.model.Product;

public class UpdateProductRequest {

    private Product product;
    private Long categoryId;

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
