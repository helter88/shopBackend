package com.artur.shop.category.repository;

import com.artur.shop.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findBySlug(String slug);

    Optional<Category> findByName(String name);

    List<Category> findByNameContaining(String query);
}
