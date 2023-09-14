package com.application.nothing.repository;

import com.application.nothing.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Find a category by name
     *
     * @param categoryName the name of the category
     * @return Optional of Category
     */
    Optional<Category> findBycategoryName(String categoryName);
    List<Category> findBycategoryNameContaining(String name);


}
