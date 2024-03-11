package com.artur.shop.admin.product.controller.dto;

public record ImageDto(
        Long id,
        String filename,
        String filetype,
        String base64
) {
}
