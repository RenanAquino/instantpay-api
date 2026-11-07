package com.instantpay.app.wallet.services;

import com.instantpay.app.user.models.User;

public interface WalletService {
    void createWallet(User user);
}
