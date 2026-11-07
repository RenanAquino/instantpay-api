CREATE TABLE wallets (
    id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,

    PRIMARY KEY (id),

    CONSTRAINT uk_wallets_user_id UNIQUE(user_id),

    CONSTRAINT fk_wallets_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);