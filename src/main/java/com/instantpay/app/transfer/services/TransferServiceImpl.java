package com.instantpay.app.transfer.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.instantpay.app.transfer.dtos.CreateTransferDTO;
import com.instantpay.app.transfer.models.Transfer;
import com.instantpay.app.transfer.repositories.TransferRepository;
import com.instantpay.app.wallet.models.Wallet;
import com.instantpay.app.wallet.repositories.WalletRepository;

public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferServiceImpl(TransferRepository transferRepository, WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public Transfer createTransfer(CreateTransferDTO data, String senderWalletEmail) {

        if (data.amount() == null ||
            data.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "Transfer amount must be greater than zero"
            );
        }

        if (transferRepository.existsByIdempotencyKey(data.idempotencyKey())) {
            throw new IllegalArgumentException(
                "Cannot create transfer: idempotency key already exists"
            );
        }

        Wallet sender = walletRepository.findByUserEmail(senderWalletEmail)
            .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiver = walletRepository.findByUserEmail(data.receiverEmail())
            .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException(
                "Cannot transfer to the same wallet"
            );
        }

        List<UUID> walletIds = new ArrayList<>(
            List.of(sender.getId(), receiver.getId())
        );

        walletIds.sort(Comparator.naturalOrder());

        Wallet firstWallet = walletRepository
            .findByIdForUpdate(walletIds.get(0))
            .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Wallet secondWallet = walletRepository
            .findByIdForUpdate(walletIds.get(1))
            .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Wallet senderWallet = firstWallet.getId().equals(sender.getId())
            ? firstWallet
            : secondWallet;

        Wallet receiverWallet = firstWallet.getId().equals(receiver.getId())
            ? firstWallet
            : secondWallet;

        if (senderWallet.getBalance().compareTo(data.amount()) < 0) {
            throw new IllegalArgumentException(
                "Insufficient balance in sender's wallet"
            );
        }

        senderWallet.subtractBalance(data.amount());
        receiverWallet.addBalance(data.amount());

        Transfer transfer = new Transfer(
            data.idempotencyKey(),
            senderWallet,
            receiverWallet,
            data.amount()
        );

        transferRepository.save(transfer);

        return transfer;

    }
    
}
