CREATE TABLE IF NOT EXISTS `meteo`.`compiled_data` (
                                                       `id` BIGINT NOT NULL,
                                                       `datetime` DATETIME(6) NULL DEFAULT NULL,
    `sensor` VARCHAR(255) NULL DEFAULT NULL,
    `station_id` BIGINT NOT NULL,
    `unit` VARCHAR(255) NULL DEFAULT NULL,
    `value` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;