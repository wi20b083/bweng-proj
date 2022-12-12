CREATE SEQUENCE IF NOT EXISTS hibernate_sequence INCREMENT BY 1 START WITH 1;

CREATE TABLE IF NOT EXISTS auction
(
    id            BIGINT   NOT NULL,
    start_date    datetime NULL,
    end_date      datetime NULL,
    status        BIT(1)   NOT NULL,
    delivery_date datetime NULL,
    total         INT      NOT NULL,
    user_id       BIGINT   NULL,
    CONSTRAINT pk_auction PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS auction_item
(
    id         BIGINT NOT NULL,
    auction_id BIGINT NULL,
    product_id BIGINT NULL,
    amount     INT    NOT NULL,
    price      INT    NOT NULL,
    CONSTRAINT pk_auctionitem PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bid
(
    id            BIGINT   NOT NULL,
    delivery_date datetime NULL,
    total_price   INT      NOT NULL,
    user_id       BIGINT   NULL,
    auction_id    BIGINT   NULL,
    CONSTRAINT pk_bid PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bid_item
(
    id         BIGINT NOT NULL,
    bid_id     BIGINT NULL,
    product_id BIGINT NULL,
    amount     INT    NOT NULL,
    price      INT    NOT NULL,
    CONSTRAINT pk_biditem PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NULL,
    img_link VARCHAR(255) NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    `role`     VARCHAR(255)          NULL,
    first_name VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    img_link   VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE auction_item
    ADD CONSTRAINT FK_AUCTIONITEM_ON_AUCTION FOREIGN KEY (auction_id) REFERENCES auction (id);

ALTER TABLE auction_item
    ADD CONSTRAINT FK_AUCTIONITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE auction
    ADD CONSTRAINT FK_AUCTION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE bid_item
    ADD CONSTRAINT FK_BIDITEM_ON_BID FOREIGN KEY (bid_id) REFERENCES bid (id);

ALTER TABLE bid_item
    ADD CONSTRAINT FK_BIDITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE bid
    ADD CONSTRAINT FK_BID_ON_AUCTION FOREIGN KEY (auction_id) REFERENCES auction (id);

ALTER TABLE bid
    ADD CONSTRAINT FK_BID_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);