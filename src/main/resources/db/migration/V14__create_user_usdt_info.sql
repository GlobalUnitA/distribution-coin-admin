CREATE TABLE user_usdt_info (
        id             BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_name      VARCHAR(100),
        wallet_address VARCHAR(128) NOT NULL,
        coin_symbol    VARCHAR(20)  NOT NULL,
        network        VARCHAR(50)  NOT NULL,
        amount         DECIMAL(36,18) NOT NULL,
        created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

        CONSTRAINT uq_user_usdt_info_wallet
            UNIQUE (wallet_address, coin_symbol, network)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
