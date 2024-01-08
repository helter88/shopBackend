package com.artur.shop.category.repository;

import com.artur.shop.common.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findBySlug(String slug);
}
