package com.artur.shop.category.repository;

import com.artur.shop.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findBySlug(String slug);
}
