CREATE TABLE `stock`
(
    `id`                bigint(15)      NOT NULL AUTO_INCREMENT,
    `stock_code`        varchar(120)    DEFAULT NULL,
    `quantity`          int(10)         DEFAULT NULL,
    PRIMARY KEY         (`id`)
);