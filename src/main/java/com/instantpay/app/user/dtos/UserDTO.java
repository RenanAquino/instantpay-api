package com.instantpay.app.user.dtos;

import java.util.UUID;

import com.instantpay.app.user.enums.AccountStatus;

public record UserDTO(
    UUID id,
    String name,
    String email,
    AccountStatus status
) {
}
