package com.instantpay.app.wallet.services;

import org.springframework.stereotype.Service;

import com.instantpay.app.user.models.User;
import com.instantpay.app.wallet.models.Wallet;
import com.instantpay.app.wallet.repositories.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {
    
    private final WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        walletRepository.save(wallet);
    }
}
