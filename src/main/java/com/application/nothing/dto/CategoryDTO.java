package com.application.nothing.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    // Getters and Setters equals, hashCode, and toString methods can be added here

}

