package com.application.nothing.service;

import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.exception.ShoppingCartNotFoundException;
import com.application.nothing.model.Category;
import com.application.nothing.repository.CategoryRepository;
import com.application.nothing.repository.ProductRepository;
import com.application.nothing.model.Product;
import com.application.nothing.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("NO Product PRESENT WITH ID = " + id)));
    }

//    @Transactional
//    public ResponseEntity<String> updateProduct(Product product) {
//        productRepository.findById(product.getProductId()).orElseThrow(() ->
//                new ProductNotFoundException("NO Product PRESENT WITH ID = " + product.getProductId()));
//        try {
//            productRepository.save(product);
//            return ResponseEntity.ok("Product updated successfully.");
//        } catch (DataIntegrityViolationException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Product.");
//        }
//    }

    @Transactional
    public ResponseEntity<String> updateProduct(Long id, Product productDetails, Long categoryId) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ProductNotFoundException("Category not found with ID: " + categoryId));

            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            //... (set other fields as necessary)
            product.setCategory(category);

            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product.");
        }
    }


    //
@Transactional
public ResponseEntity<String> createNewProduct(Product product, Long categoryId) {
    try {
        if (productRepository.findByname(product.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already exists.");
        }

        // Fetch the category from the database using the category ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("NO Category PRESENT WITH ID = " + categoryId));

        // Set the category on the product object
        product.setCategory(category);

        productRepository.save(product);
        return ResponseEntity.ok("Product created successfully.");
    } catch (DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Product.");
    }
}


    @Transactional
    public ResponseEntity<String> deleteById(Long id) {
        productRepository.findById(id).orElseThrow(() ->
                new ShoppingCartNotFoundException("NO Product PRESENT WITH ID = " + id));
        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully.");

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Product.!!");
            // Handle the unique constraint violation, e.g., return an error message to the user

        }

    }

    public List<Product> findByCategoryId(Long categoryId) {
        try {
            return productRepository.findAllByCategory_CategoryId(categoryId);
//        } catch (DataIntegrityViolationException ex) {
//            return (List<Product>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in finding Products by catagory.");
        } catch (DataIntegrityViolationException ex) {
            System.err.println("Error in finding Products by category.");
            ex.printStackTrace();
            return Collections.emptyList();


        }


    }
}
