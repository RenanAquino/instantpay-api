CREATE TABLE users (
    id BINARY(16) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    account_status VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);