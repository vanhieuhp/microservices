CREATE TABLE IF NOT EXISTS loans
(
    loan_number        BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mobile_number      VARCHAR(20)        NOT NULL,
    loan_type          VARCHAR(30)        NOT NULL,
    total_loan        INT                NOT NULL,
    amount_paid       INT                NOT NULL,
    outstanding_amount INT                NOT NULL,
    active_sw         BOOLEAN            NOT NULL DEFAULT FALSE,
    created_at        DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by        VARCHAR(50)        NOT NULL,
    updated_at        DATETIME ON UPDATE CURRENT_TIMESTAMP,
    updated_by        VARCHAR(50)
    ) ENGINE = InnoDB;