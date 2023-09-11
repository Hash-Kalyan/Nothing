package com.application.nothing.controller;


import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Fetching all categories");
        return ResponseEntity.ok(categoryService.findAll());
    }

    @ApiOperation(value = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        logger.info("Fetching category with ID: {}", id);
        return categoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @ApiOperation(value = "Create a new category")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Creating new category");
        return ResponseEntity.ok(categoryService.save(categoryDTO));
    }

    @ApiOperation(value = "Update category by ID")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        logger.info("Updating category with ID: {}", id);
        categoryDTO.setCategoryId(id);
        return ResponseEntity.ok(categoryService.save(categoryDTO));
    }

    @ApiOperation(value = "Delete category by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        logger.info("Deleting category with ID: {}", id);
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




//package com.application.nothing.controller;
//
//import com.application.nothing.model.Category;
//import com.application.nothing.service.CategoryService;
//import com.application.nothing.exception.CategoryNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/categories")
//public class CategoryController {
//
//    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @GetMapping
//    public ResponseEntity<List<Category>> getAllCategories() {
//        return ResponseEntity.ok(categoryService.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
//        return categoryService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        return ResponseEntity.ok(categoryService.save(category));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
//        category.setCategoryId(id);
//        return ResponseEntity.ok(categoryService.save(category));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
//        categoryService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
