CREATE TABLE auction
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    user_id            BIGINT                NULL,
    start_date_time    timestamp             NULL,
    delivery_date_time timestamp             NULL,
    end_date_time      timestamp             NULL,
    status             VARCHAR(255)          NULL,
    CONSTRAINT pk_auction PRIMARY KEY (id)
);

CREATE TABLE auction_item
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    auction_id    BIGINT                NULL,
    product_id    BIGINT                NULL,
    amount        INT                   NULL,
    cost_per_unit INT                   NULL,
    total         INT                   NULL,
    CONSTRAINT pk_auction_item PRIMARY KEY (id)
);

CREATE TABLE bid
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    user_id            BIGINT                NULL,
    delivery_date_time timestamp             NULL,
    status             VARCHAR(255)          NULL,
    auction_id         BIGINT                NULL,
    CONSTRAINT pk_bid PRIMARY KEY (id)
);

CREATE TABLE bid_item
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    bid_id        BIGINT                NULL,
    product_id    BIGINT                NULL,
    amount        INT                   NULL,
    cost_per_unit INT                   NULL,
    total         INT                   NULL,
    CONSTRAINT pk_bid_item PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    description   VARCHAR(255)          NULL,
    image_path    VARCHAR(255)          NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    email      VARCHAR(255)          NULL,
    user_name  VARCHAR(255)          NULL,
    password   VARCHAR(255)          NULL,
    status     VARCHAR(255)          NULL,
    image_path VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    roles   INT    NULL
);

ALTER TABLE auction_item
    ADD CONSTRAINT FK_AUCTION_ITEM_ON_AUCTION FOREIGN KEY (auction_id) REFERENCES auction (id);

ALTER TABLE auction_item
    ADD CONSTRAINT FK_AUCTION_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE auction
    ADD CONSTRAINT FK_AUCTION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE bid_item
    ADD CONSTRAINT FK_BID_ITEM_ON_BID FOREIGN KEY (bid_id) REFERENCES bid (id);

ALTER TABLE bid_item
    ADD CONSTRAINT FK_BID_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE bid
    ADD CONSTRAINT FK_BID_ON_AUCTION FOREIGN KEY (auction_id) REFERENCES auction (id);

ALTER TABLE bid
    ADD CONSTRAINT FK_BID_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES user (id);