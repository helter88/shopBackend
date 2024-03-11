package com.artur.shop.admin.product.repository;

import com.artur.shop.admin.product.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.id NOT IN (SELECT pi.image.id FROM ProductImage pi)")
    void deleteOrphanImages();
}
