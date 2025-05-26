CREATE TABLE customer
(
    customer_id   BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(100),
    mobile_number VARCHAR(20),
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(50)  NOT NULL,
    updated_at    DATETIME ON UPDATE CURRENT_TIMESTAMP,
    updated_by    VARCHAR(50),
    PRIMARY KEY (customer_id),
    UNIQUE KEY uk_customer_email (email)
) ENGINE = InnoDB;

CREATE TABLE accounts
(
    account_number BIGINT      NOT NULL,
    customer_id    BIGINT      NOT NULL,
    account_type   VARCHAR(30) NOT NULL,
    branch_address VARCHAR(150),
    created_at     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     VARCHAR(50) NOT NULL,
    updated_at     DATETIME ON UPDATE CURRENT_TIMESTAMP,
    updated_by     VARCHAR(50),
    PRIMARY KEY (account_number),
    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id)
            ON DELETE CASCADE
) ENGINE = InnoDB;
