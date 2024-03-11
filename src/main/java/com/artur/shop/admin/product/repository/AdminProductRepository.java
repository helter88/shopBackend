package com.artur.shop.admin.product.repository;

import com.artur.shop.admin.product.model.AdminProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {

    @Query("SELECT p FROM AdminProduct p LEFT JOIN FETCH p.productImages WHERE p.id = :id")
    AdminProduct findByIdWithImages(Long id);

    @Query(value = "SELECT p FROM AdminProduct p LEFT JOIN FETCH p.productImages",
            countQuery = "SELECT count(p) FROM AdminProduct p")
    Page<AdminProduct> findAllWithImages(Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImage pi WHERE pi.product.id = :productId")
    void deleteProductImagesByProductId(Long productId);
}
