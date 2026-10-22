package com.instantpay.app.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
    @NotBlank(message = "Name is required")
    String name
) {
}
