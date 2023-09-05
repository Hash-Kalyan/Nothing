package com.application.nothing.controller;

import com.application.nothing.model.Category;
import com.application.nothing.service.CategoryService;
import com.application.nothing.exception.CategoryNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    // Fetch all categories
    @Operation(summary = "Get All Categories", description = "Fetches a list of all product categories available in the database.")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // Fetch a single category by ID
    @Operation(summary = "Get Category by ID", description = "Fetches the details of a specific product category identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Category fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new category
    @Operation(summary = "Create Category", description = "Creates a new product category with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Category created successfully")
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.save(category));
    }

    // Update a category by ID
    @Operation(summary = "Update Category", description = "Updates the details of an existing product category identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setCategoryId(id);
        return ResponseEntity.ok(categoryService.save(category));
    }

    // Delete a category by ID
    @Operation(summary = "Delete Category", description = "Deletes a product category identified by its ID from the database.", responses = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}