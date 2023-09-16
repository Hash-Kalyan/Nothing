package com.application.nothing.controller;

import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.exception.CategoryAlreadyExistsException;
import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get all categories")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        logger.info("Initiating request to fetch all categories");
        List<CategoryDTO> categories = categoryService.findAll();
        logger.info("Successfully fetched {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @ApiOperation(value = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        logger.info("Initiating request to fetch category with ID: {}", id);
        return categoryService.findById(id)
                .map(category -> {
                    logger.info("Successfully fetched category with ID: {}", id);
                    return ResponseEntity.ok(category);
                })
                .orElseThrow(() -> {
                    logger.error("Category not found with ID: {}", id);
                    return new CategoryNotFoundException("Category not found");
                });
    }

    @ApiOperation(value = "Create a new category")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Initiating request to create a new category with name: {}", categoryDTO.getCategoryName());
        CategoryDTO createdCategory = categoryService.save(categoryDTO);
        logger.info("Successfully created category with ID: {}", createdCategory.getCategoryId());
        return ResponseEntity.ok(createdCategory);
    }

    @ApiOperation(value = "Update category by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Initiating request to update category with ID: {}", id);
        categoryDTO.setCategoryId(id);
        CategoryDTO updatedCategory = categoryService.save(categoryDTO);
        logger.info("Successfully updated category with ID: {}", id);
        return ResponseEntity.ok(updatedCategory);
    }

    @ApiOperation(value = "Delete category by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Initiating request to delete category with ID: {}", id);
        categoryService.deleteById(id);
        logger.info("Successfully deleted category with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<String> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
