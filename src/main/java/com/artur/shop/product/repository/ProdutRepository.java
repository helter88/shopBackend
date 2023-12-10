package com.artur.shop.product.repository;

import com.artur.shop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutRepository extends JpaRepository<Product, Long> {
}
