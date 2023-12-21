package com.artur.shop.product.repository;

import com.artur.shop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);
}
