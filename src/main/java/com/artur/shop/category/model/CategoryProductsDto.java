package com.artur.shop.category.model;

import com.artur.shop.product.model.ProductListDto;
import org.springframework.data.domain.Page;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
