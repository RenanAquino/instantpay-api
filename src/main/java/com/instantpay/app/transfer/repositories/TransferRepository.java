package com.instantpay.app.transfer.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instantpay.app.transfer.models.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    boolean existsByIdempotencyKey(UUID idempotencyKey);
}
