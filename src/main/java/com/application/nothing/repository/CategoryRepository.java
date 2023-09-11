package com.application.nothing.repository;

import com.application.nothing.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find a category by name
     *
     * @param categoryName the name of the category
     * @return Optional of Category
     */
    Optional<Category> findByCategoryName(String categoryName);
}
