CREATE TABLE `purchases`
(
    `id`                bigint(15)      NOT NULL AUTO_INCREMENT,
    `purchase_number`   varchar(120)    DEFAULT NULL,
    `stock_code`        varchar(120),
    `price`             decimal(10, 2),
    `quantity`          int(10),
    PRIMARY KEY         (`id`)
);