package com.application.nothing.controller;

import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.dto.ProductDTO;
import com.application.nothing.exception.ProductAlreadyExistsException;
import com.application.nothing.exception.ProductNotFoundException;
import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.service.CategoryService;
import com.application.nothing.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get all products")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @ApiOperation(value = "Get product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        Optional<ProductDTO> productOptional = productService.getProductById(id);
        return productOptional.map(productDTO -> {
            logger.info("Product found: {}", productDTO);
            return ResponseEntity.ok(productDTO);
        }).orElseThrow(() -> {
            logger.error("Product not found with ID: {}", id);
            return new ProductNotFoundException("Product not found");
        });
    }

    @ApiOperation(value = "Get products by category ID")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        logger.info("Fetching products for category ID: {}", categoryId);
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @ApiOperation(value = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        logger.info("Creating new product: {}", productDTO);
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

//    @ApiOperation(value = "Update the existing product")
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDTO> updateProduct(
//            @PathVariable Long id,
//            @RequestBody ProductDTO productDTO
//    ) {
//        logger.info("Updating the existing product: {}", productDTO);
//        // Fetch the CategoryDTO based on the categoryId in productDTO
//        Long categoryId = productDTO.getCategoryId();
//        CategoryDTO categoryDTO = categoryService.findById(categoryId)
//                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//
//        // Call the service method with both ProductDTO and CategoryDTO
//        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, categoryDTO);
//
//        return ResponseEntity.ok(updatedProduct);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ResponseEntity<String>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        product.setProductId(id);
//        // You might want to do some checks or modifications here before saving
//        return ResponseEntity.ok(productService.updateProduct(product));
//    }

    // ... (other methods)


//    @PostMapping("/create")
//    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
//        // Fetch the CategoryDTO based on the categoryId in productDTO
//        Long categoryId = productDTO.getCategoryId();
//        CategoryDTO categoryDTO = categoryService.findById(categoryId)
//            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
//
//        // Call the service method with both ProductDTO and CategoryDTO
//        ProductDTO createdProduct = productService.createProduct(productDTO, categoryDTO);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//    }


    @ApiOperation(value = "Update product by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO
    ) {
        logger.info("Updating product with ID: {}", id);

        // Call the service method with ProductDTO
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);

        logger.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }


    @ApiOperation(value = "Delete product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get products by name")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductDTO>> getProductsByName(@PathVariable String name) {
        logger.info("Fetching products by name: {}", name);
        List<ProductDTO> products = productService.getProductsByName(name);
        if (products.isEmpty()) {
            logger.warn("No products found with name: {}", name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // Exception handler for ProductNotFoundException
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        logger.error("Product not found exception: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}

