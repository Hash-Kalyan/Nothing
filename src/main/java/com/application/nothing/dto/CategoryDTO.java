package com.application.nothing.dto;

import jakarta.validation.constraints.NotBlank;


public class CategoryDTO {

    private Long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    // Getters and Setters

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // Constructors

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    // equals, hashCode, and toString methods can be added here
}

