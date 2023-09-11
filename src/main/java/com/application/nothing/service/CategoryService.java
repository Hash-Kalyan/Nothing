package com.application.nothing.service;


import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.model.Category;
import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Fetch all categories
     *
     * @return List of CategoryDTO
     */
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find a category by ID
     *
     * @param id Category ID
     * @return Optional of CategoryDTO
     */
    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    /**
     * Save a category
     *
     * @param categoryDTO Category DTO object
     * @return Saved CategoryDTO object
     */
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    /**
     * Delete a category by ID
     *
     * @param id Category ID
     */
    public void deleteById(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.deleteById(id);
    }

//    private CategoryDTO convertToDTO(Category category) {
//        // Implement the conversion logic to convert Category entity to DTO
//        return new CategoryDTO(category.getCategoryName());
//    }
//
//    private Category convertToEntity(CategoryDTO categoryDTO) {
//        // Implement the conversion logic to convert Category DTO to entity
//        return new Category(categoryDTO.getCategoryName());
//    }

    private CategoryDTO convertToDTO(Category category) {
        // Implement the conversion logic to convert Category entity to DTO
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        return categoryDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        // Implement the conversion logic to convert Category DTO to entity
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        return category;
    }

}








//package com.application.nothing.service;
//
//import com.application.nothing.repository.CategoryRepository;
//import com.application.nothing.model.Category;
//import com.application.nothing.exception.CategoryNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CategoryService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//    public List<Category> findAll() {
//        return categoryRepository.findAll();
//    }
//
//    public Optional<Category> findById(Long id) {
//        return categoryRepository.findById(id);
//    }
//
//    public Category save(Category category) {
//        return categoryRepository.save(category);
//    }
//
//    public void deleteById(Long id) {
//        categoryRepository.deleteById(id);
//    }
//}
//
