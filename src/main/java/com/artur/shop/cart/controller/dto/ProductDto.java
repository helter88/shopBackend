package com.artur.shop.cart.controller.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price, String currency, String slug) {
}
