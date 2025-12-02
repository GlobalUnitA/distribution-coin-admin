CREATE TABLE usdt_distribution_batch (
     id           BIGINT AUTO_INCREMENT PRIMARY KEY,
     coin_symbol  VARCHAR(20)  NOT NULL,
     network      VARCHAR(50)  NOT NULL,
     total_amount DECIMAL(36,18) NOT NULL,
     status       VARCHAR(20)  NOT NULL, -- READY / PROCESSING / DONE / FAILED
     progress     INT          NOT NULL DEFAULT 0, -- 0 ~ 100
     created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usdt_distribution_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    name VARCHAR(100),
    wallet_address VARCHAR(150) NOT NULL,
    amount DECIMAL(36,18) NOT NULL,
    status VARCHAR(20) NOT NULL, --PENDING / SUCCESS / FAILED
    txid VARCHAR(130),
    error_message VARCHAR(255),
    created_at     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usdt_distribution_item_batch
                                    FOREIGN KEY (batch_id)
                                    REFERENCES usdt_distribution_batch(id)
                                    ON DELETE CASCADE
);

CREATE INDEX idx_usdt_distribution_item_batch
ON usdt_distribution_item (batch_id);