CREATE TABLE member_wallet (
                               id            BIGINT NOT NULL AUTO_INCREMENT,
                               member_id     BIGINT NOT NULL,
                               wallet_addr   VARCHAR(255) NOT NULL,
                               network       VARCHAR(50)  NOT NULL,
                               created_at    DATETIME(6)  NOT NULL,
                               updated_at    DATETIME(6)  NOT NULL,
                               PRIMARY KEY (id),
                               UNIQUE KEY uk_member_wallet_addr (wallet_addr),
                               KEY idx_member_wallet_member_id (member_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;