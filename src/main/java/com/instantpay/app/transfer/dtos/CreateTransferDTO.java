package com.instantpay.app.transfer.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTransferDTO(

    @NotNull
    UUID idempotencyKey,
    
    @NotBlank
    String receiverEmail,

    @NotNull
    @DecimalMin (value = "0.01")
    @Digits (integer = 17, fraction = 2)
    BigDecimal amount

) {}
