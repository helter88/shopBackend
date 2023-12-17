package com.artur.shop.admin.product.controller.dto;

import com.artur.shop.admin.product.model.AdminProductCurrency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record AdminProductDto(
        @NotBlank
        @Length(min=2)
        String name,
        @NotBlank
        @Length(min=2)
        String category,
        String description,
        @NotBlank
        @Min(0)
        BigDecimal price,
        AdminProductCurrency currency) {
}
