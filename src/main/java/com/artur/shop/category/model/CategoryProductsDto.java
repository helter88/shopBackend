package com.artur.shop.category.model;

import com.artur.shop.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record CategoryProductsDto(Category category, Page<Product> products) {
}
