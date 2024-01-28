package com.artur.shop.cart.controller.dto;

import java.util.List;

public record CartSummaryDto(Long id, List<CartSummaryItemDto> items, SummaryDto summary) {
}
