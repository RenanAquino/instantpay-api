package com.instantpay.app.wallet.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.instantpay.app.wallet.models.Wallet;

import jakarta.persistence.LockModeType;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    
    Optional<Wallet> findByUserId(UUID userId);
    Optional<Wallet> findByUserEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT w FROM Wallet w
        WHERE w.id = :id
    """)
    Optional<Wallet> findByIdForUpdate(UUID id);
}
