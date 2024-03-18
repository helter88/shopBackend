package com.artur.shop.common.repository;

import com.artur.shop.common.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    List<Product> findByCategoryId(Long id);
    List<Product> findTop10ByDiscountPriceNotNull();

    List<Product> findByNameContaining(String query, Pageable limit);

    List<Product> findByDescriptionContaining(String query, Pageable limit);
}
