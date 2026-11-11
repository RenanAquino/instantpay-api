CREATE TABLE transfers (
    id BINARY(16) NOT NULL,
    idempotency_key BINARY(16) NOT NULL,

    sender_wallet_id BINARY(16) NOT NULL,
    receiver_wallet_id BINARY(16) NOT NULL,

    amount DECIMAL(19, 2) NOT NULL,

    status VARCHAR(255) NOT NULL,
    started_at TIMESTAMP(6) NOT NULL,
    completed_at TIMESTAMP(6) NULL,

    PRIMARY KEY (id),

    CONSTRAINT uk_transfers_idempotency_key
        UNIQUE (idempotency_key),

    CONSTRAINT fk_transfers_sender_wallet
        FOREIGN KEY (sender_wallet_id)
        REFERENCES wallets(id),

    CONSTRAINT fk_transfers_receiver_wallet
        FOREIGN KEY (receiver_wallet_id)
        REFERENCES wallets(id)
);