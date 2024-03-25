package com.artur.shop.userData.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDataDto(
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
        String phone
) {
}
