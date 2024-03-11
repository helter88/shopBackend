package com.artur.shop.admin.product.repository;

import com.artur.shop.admin.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    void deleteByProductId(Long id);
}
