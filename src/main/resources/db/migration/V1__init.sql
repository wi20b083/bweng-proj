CREATE TABLE auction
(
    id            BIGINT   NOT NULL,
    start_date    datetime NOT NULL,
    end_date      datetime NOT NULL,
    status        BIT(1)   NOT NULL,
    delivery_date datetime NOT NULL,
    user_id       BIGINT   NULL,
    total         INT      NOT NULL,
    CONSTRAINT pk_auction PRIMARY KEY (id)
);

CREATE TABLE auction_item
(
    id         BIGINT NOT NULL,
    auction_id BIGINT NULL,
    product_id BIGINT NULL,
    amount     INT    NOT NULL,
    price      INT    NOT NULL,
    CONSTRAINT pk_auctionitem PRIMARY KEY (id)
);

CREATE TABLE bid
(
    id            BIGINT   NOT NULL,
    delivery_date datetime NOT NULL,
    user_id       BIGINT   NULL,
    total         INT      NOT NULL,
    auction_id    BIGINT   NULL,
    CONSTRAINT pk_bid PRIMARY KEY (id)
);

CREATE TABLE bid_item
(
    id         BIGINT NOT NULL,
    bid_id     BIGINT NULL,
    product_id BIGINT NULL,
    amount     INT    NOT NULL,
    price      INT    NOT NULL,
    CONSTRAINT pk_biditem PRIMARY KEY (id)
);

CREATE TABLE product
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NOT NULL,
    img_link VARCHAR(255) NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)          NOT NULL,
    last_name  VARCHAR(255)          NOT NULL,
    img_link   VARCHAR(255)          NOT NULL,
    user_name  VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    status     BIT(1)                NOT NULL,
    street     VARCHAR(255)          NOT NULL,
    street_nr  INT                   NOT NULL,
    zip        INT                   NOT NULL,
    city       VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id BIGINT       NOT NULL,
    `role`  VARCHAR(255) NOT NULL
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (user_name);

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

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES user (id);