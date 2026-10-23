package com.instantpay.app.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Email is required")
    @Email (message = "Email should be valid")
    String email,
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must be between {min} and {max} characters")
    String password
) {
}
