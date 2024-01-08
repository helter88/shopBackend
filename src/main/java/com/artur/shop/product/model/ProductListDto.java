package com.artur.shop.product.model;

import java.math.BigDecimal;

public record ProductListDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String currency,
        String image,
        String slug
) {
}
