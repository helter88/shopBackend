package com.artur.shop.order.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderDto(
        @NotBlank
        String firstname,
        @NotBlank
        String lastname,
        @NotBlank
        String street,
        @NotBlank
        String zipcode,
        @NotBlank
        String city,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotNull
        Long cartId
) {
}
