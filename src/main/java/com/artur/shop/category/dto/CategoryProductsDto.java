package com.artur.shop.category.dto;

import com.artur.shop.common.model.Category;
import com.artur.shop.common.dto.ProductListDto;
import org.springframework.data.domain.Page;

public record CategoryProductsDto(Category category, Page<ProductListDto> products) {
}
