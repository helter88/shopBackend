package com.artur.shop.common.dto;

import com.artur.shop.admin.product.model.ProductImage;

import java.math.BigDecimal;

public record ProductListDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String currency,
        String slug,
        BigDecimal discountPrice,

        ProductImage image
) {
}
