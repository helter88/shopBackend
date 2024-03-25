package com.artur.shop.cart.controller.dto;

import com.artur.shop.admin.product.model.ProductImage;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price, String currency, String slug,  ProductImage image) {
}
