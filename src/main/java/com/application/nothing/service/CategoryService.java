package com.application.nothing.service;

import com.application.nothing.dto.CategoryDTO;
import com.application.nothing.exception.CategoryAlreadyExistsException;
import com.application.nothing.exception.CategoryNotFoundException;
import com.application.nothing.model.Category;
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

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDto)  // updated method name
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> findById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDto);  // updated method name
    }

//    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//        categoryRepository.findBycategoryName(categoryDTO.getCategoryName())
//                .ifPresent(category -> {
//                    throw new CategoryAlreadyExistsException("Category with name '" + categoryDTO.getCategoryName() + "' already exists");
//                });
//
//        Category category = convertToEntity(categoryDTO);
//        Category savedCategory = categoryRepository.save(category);
//        return convertToDTO(savedCategory);
//    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        // Check if a category with the specified name already exists
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())) {
            throw new CategoryAlreadyExistsException("A category with the name '" + categoryDTO.getCategoryName() + "' already exists");
        }

        // Convert DTO to Entity, save it and then convert the saved entity back to DTO
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with ID " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }


    private CategoryDTO convertToDto(Category category) {
        // Implementation of the conversion from Entity to DTO
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        // ... (set other fields as necessary)

        return categoryDTO;
    }


        private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        return category;
    }
}
