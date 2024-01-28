package com.artur.shop.cart.controller.dto;

import java.math.BigDecimal;

public record CartSummaryItemDto(Long id, int quantity, ProductDto product, BigDecimal lineValue) {
}
