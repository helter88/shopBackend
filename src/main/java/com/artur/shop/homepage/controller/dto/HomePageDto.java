package com.artur.shop.homepage.controller.dto;

import com.artur.shop.common.model.Product;

import java.util.List;

public record HomePageDto(List<Product> discountedProducts) {
}
