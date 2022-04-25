CREATE TABLE IF NOT EXISTS `meteo`.`stations` (
                                                  `id_stations` BIGINT NOT NULL,
                                                  `altitude` DOUBLE NULL DEFAULT NULL,
                                                  `description` VARCHAR(255) NULL DEFAULT NULL,
    `latitude` DOUBLE NULL DEFAULT NULL,
    `location` VARCHAR(255) NULL DEFAULT NULL,
    `longitude` DOUBLE NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    `owner_email` VARCHAR(255) NULL DEFAULT NULL,
    `sensors` VARCHAR(255) NULL DEFAULT NULL,
    `type` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id_stations`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
