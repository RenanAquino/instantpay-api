package com.instantpay.app.transfer.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.instantpay.app.transfer.enums.TransferStatus;
import com.instantpay.app.wallet.models.Wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transfers")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transfer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID idempotencyKey;

    @ManyToOne
    @JoinColumn(name = "sender_wallet_id", nullable = false)
    private Wallet senderWallet;

    @ManyToOne
    @JoinColumn(name = "receiver_wallet_id", nullable = false)
    private Wallet receiverWallet;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransferStatus status = TransferStatus.PENDING;

    @Column(nullable = false)
    private Instant startedAt = Instant.now();

    private Instant completedAt;

    public Transfer(UUID idempotencyKey, Wallet senderWallet, Wallet receiverWallet, BigDecimal amount) {
        this.idempotencyKey = idempotencyKey;
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
    }

    public void complete() {
        this.status = TransferStatus.COMPLETED;
        this.completedAt = Instant.now();
    }

    public void fail() {
        this.status = TransferStatus.FAILED;
    }
}
