package com.instantpay.app.transfer.services;

import com.instantpay.app.transfer.dtos.CreateTransferDTO;
import com.instantpay.app.transfer.models.Transfer;

public interface TransferService {
    Transfer createTransfer(CreateTransferDTO data, String senderWalletEmail);
}
