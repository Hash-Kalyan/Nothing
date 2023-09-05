package com.application.nothing.controller;

import com.application.nothing.model.Product;
import com.application.nothing.service.ProductService;
import com.application.nothing.exception.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    // Fetch all products
    @Operation(summary = "Get All Products", description = "Fetches a list of all products available in the inventory.")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    // Fetch a single product by ID
    @Operation(summary = "Get Product by ID", description = "Fetches the details of a specific product identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Fetch products by category ID
    @Operation(summary = "Get Products by Category ID", description = "Fetches a list of products that belong to a specific category, identified by the category ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Optional<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    // Create a new product
    @Operation(summary = "Create Product", description = "Creates a new product with the provided details.", responses = {
            @ApiResponse(responseCode = "200", description = "Product created successfully")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    // Update a product by ID
    @Operation(summary = "Update Product", description = "Updates the details of an existing product identified by its ID.", responses = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setProductId(id);
        return ResponseEntity.ok(productService.save(product));
    }

    // Delete a product by ID
    @Operation(summary = "Delete Product", description = "Deletes a product identified by its ID from the inventory.", responses = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}