package com.artur.shop.common.repository;

import com.artur.shop.common.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    List<Product> findByCategoryId(Long id);
    List<Product> findTop10ByDiscountPriceNotNull();

    List<Product> findByNameContaining(String query, Pageable limit);

    List<Product> findByDescriptionContaining(String query, Pageable limit);

    @Query("SELECT p FROM Product p LEFT JOIN Category c ON p.categoryId = c.id " +
            "WHERE p.name LIKE %:query% OR p.description LIKE %:query% OR c.name LIKE %:query% " +
            "GROUP BY p.id " +
            "ORDER BY " +
            "CASE WHEN p.name LIKE %:query% THEN 1 " +
            "     WHEN p.description LIKE %:query% THEN 2 " +
            "     WHEN c.name LIKE %:query% THEN 3 " +
            "ELSE 4 END, p.name, p.description, c.name")
    Page<Product> searchByQuery(@Param("query") String query, Pageable pageable);
}
