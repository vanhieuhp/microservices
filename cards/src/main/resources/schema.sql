CREATE TABLE IF NOT EXISTS cards
(
    card_number   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mobile_number VARCHAR(20)        NOT NULL,
    card_type     VARCHAR(30)        NOT NULL,
    total_limit   INT                NOT NULL,
    amount_used   INT                NOT NULL,
    available_amount INT             NOT NULL,
    active_sw     BOOLEAN            NOT NULL DEFAULT FALSE,
    created_at    DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(50)        NOT NULL,
    updated_at    DATETIME ON UPDATE CURRENT_TIMESTAMP,
    updated_by    VARCHAR(50)
) ENGINE = InnoDB;