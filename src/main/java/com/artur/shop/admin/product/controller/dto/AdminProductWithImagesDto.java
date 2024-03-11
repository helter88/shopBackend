package com.artur.shop.admin.product.controller.dto;

import com.artur.shop.admin.product.model.AdminProductCurrency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record AdminProductWithImagesDto(
        @NotBlank
        @Length(min=2)
        String name,
        @NotNull
        Long categoryId,
        String description,

        @Min(0)
        BigDecimal price,
        AdminProductCurrency currency,
        @NotBlank
        @Length(min=2)
        String slug,
        @Min(0)
        BigDecimal discountPrice,
        List<ImageDto> images
) {
}
