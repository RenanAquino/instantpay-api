package com.instantpay.app.wallet.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instantpay.app.wallet.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    
    Wallet findByUserId(UUID userId);
    Wallet findByUserEmail(String email);
}
