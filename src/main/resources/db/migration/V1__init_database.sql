CREATE SEQUENCE IF NOT EXISTS hibernate_sequence INCREMENT BY 1 START WITH 1;

CREATE OR REPLACE TABLE auction
(
    id            BIGINT                NOT NULL,
    delivery_date datetime DEFAULT NULL NULL,
    end_date      datetime DEFAULT NULL NULL,
    start_date    datetime DEFAULT NULL NULL,
    status        BIT                   NOT NULL,
    total         INT                   NOT NULL,
    user_id       BIGINT   DEFAULT NULL NULL,
    CONSTRAINT PK_AUCTION PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE auction_item
(
    id         BIGINT              NOT NULL,
    amount     INT                 NOT NULL,
    price      INT                 NOT NULL,
    auction_id BIGINT DEFAULT NULL NULL,
    product_id BIGINT DEFAULT NULL NULL,
    CONSTRAINT PK_AUCTION_ITEM PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE bid
(
    id            BIGINT                NOT NULL,
    delivery_date datetime DEFAULT NULL NULL,
    total_price   INT                   NOT NULL,
    auction_id    BIGINT   DEFAULT NULL NULL,
    user_id       BIGINT   DEFAULT NULL NULL,
    total         INT                   NOT NULL,
    CONSTRAINT PK_BID PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE bid_item
(
    id         BIGINT              NOT NULL,
    amount     INT                 NOT NULL,
    price      INT                 NOT NULL,
    bid_id     BIGINT DEFAULT NULL NULL,
    product_id BIGINT DEFAULT NULL NULL,
    CONSTRAINT PK_BID_ITEM PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE flyway_schema_history
(
    installed_rank INT                     NOT NULL,
    version        VARCHAR(50)             NULL,
    `description`  VARCHAR(200)            NOT NULL,
    type           VARCHAR(20)             NOT NULL,
    script         VARCHAR(1000)           NOT NULL,
    checksum       INT       DEFAULT NULL  NULL,
    installed_by   VARCHAR(100)            NOT NULL,
    installed_on   timestamp DEFAULT NOW() NOT NULL,
    execution_time INT                     NOT NULL,
    success        BIT                     NOT NULL,
    CONSTRAINT PK_FLYWAY_SCHEMA_HISTORY PRIMARY KEY (installed_rank)
);

CREATE OR REPLACE TABLE hibernate_sequence
(
    next_val BIGINT DEFAULT NULL NULL
);

CREATE OR REPLACE TABLE product
(
    id       BIGINT       NOT NULL,
    img_link VARCHAR(255) NULL,
    name     VARCHAR(255) NULL,
    CONSTRAINT PK_PRODUCT PRIMARY KEY (id)
);

CREATE OR REPLACE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    first_name VARCHAR(255)          NULL,
    img_link   VARCHAR(255)          NULL,
    last_name  VARCHAR(255)          NULL,
    `role`     VARCHAR(255)          NULL,
    email      VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    status     BIT                   NOT NULL,
    user_name  VARCHAR(255)          NOT NULL,
    address    VARCHAR(255)          NULL,
    CONSTRAINT PK_USER PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (user_name)
);

CREATE OR REPLACE INDEX FK2f5tf5bn1cn13dlhruo03ui5q ON auction (user_id);

CREATE OR REPLACE INDEX FK4abkntgv9nvsfi86p7kfl63au ON bid (user_id);

CREATE OR REPLACE INDEX FKevh9kx1rt5apknnhfmah0tlrq ON auction_item (auction_id);

CREATE OR REPLACE INDEX FKh0r6riadf4veuwtc6iyldxrnp ON bid_item (bid_id);

CREATE OR REPLACE INDEX FKhexc6i4j8i0tmpt8bdulp6g3g ON bid (auction_id);

CREATE OR REPLACE INDEX FKj4ceabb0ywf9t6rcyncc0vodx ON bid_item (product_id);

CREATE OR REPLACE INDEX FKqhyqkjhtitomjws3tfjm07b45 ON auction_item (product_id);

CREATE OR REPLACE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE auction
    ADD CONSTRAINT FK2f5tf5bn1cn13dlhruo03ui5q FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE bid
    ADD CONSTRAINT FK_BID_ON_USER FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE auction_item
    ADD CONSTRAINT FKevh9kx1rt5apknnhfmah0tlrq FOREIGN KEY (auction_id) REFERENCES auction (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE bid_item
    ADD CONSTRAINT FKh0r6riadf4veuwtc6iyldxrnp FOREIGN KEY (bid_id) REFERENCES bid (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE bid
    ADD CONSTRAINT FKhexc6i4j8i0tmpt8bdulp6g3g FOREIGN KEY (auction_id) REFERENCES auction (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE bid_item
    ADD CONSTRAINT FKj4ceabb0ywf9t6rcyncc0vodx FOREIGN KEY (product_id) REFERENCES product (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE auction_item
    ADD CONSTRAINT FKqhyqkjhtitomjws3tfjm07b45 FOREIGN KEY (product_id) REFERENCES product (id) ON UPDATE RESTRICT ON DELETE RESTRICT;