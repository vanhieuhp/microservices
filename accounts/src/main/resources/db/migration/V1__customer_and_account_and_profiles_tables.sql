CREATE TABLE IF NOT EXISTS customer
(
    customer_id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name          VARCHAR(100)       NOT NULL,
    email         VARCHAR(100),
    mobile_number VARCHAR(20),
    active_sw     BOOLEAN            NOT NULL DEFAULT FALSE,
    created_at    DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(50)        NOT NULL,
    updated_at    DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by    VARCHAR(50),
    UNIQUE KEY uk_customer_email (email),
    INDEX idx_customer_mobile_number (mobile_number)
    ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS accounts
(
    account_number BIGINT PRIMARY KEY NOT NULL,
    account_type   VARCHAR(30)        NOT NULL,
    branch_address VARCHAR(150),
    mobile_number  VARCHAR(20),
    active_sw      BOOLEAN            NOT NULL DEFAULT FALSE,
    created_at     DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     VARCHAR(50)        NOT NULL,
    updated_at     DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     VARCHAR(50),
    INDEX idx_accounts_mobile_number (mobile_number)
    ) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS profile
(
    profile_id     BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name           VARCHAR(100)       NOT NULL,
    mobile_number  VARCHAR(20)        NOT NULL,
    active_sw      BOOLEAN            NOT NULL DEFAULT FALSE,
    account_number BIGINT,
    card_number    BIGINT,
    loan_number    BIGINT,
    created_at     DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by     VARCHAR(50)        NOT NULL,
    updated_at     DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_by     VARCHAR(50),
    CONSTRAINT fk_profile_account
    FOREIGN KEY (account_number) REFERENCES accounts (account_number),
    INDEX idx_profile_mobile_number (mobile_number),
    INDEX idx_profile_account_number (account_number),
    INDEX idx_profile_card_number (card_number),
    INDEX idx_profile_loan_number (loan_number)
    ) ENGINE = InnoDB;