CREATE TABLE IF NOT EXISTS `meteo`.`users` (
                                               `id_user` BIGINT NOT NULL,
                                               `active` BIT(1) NULL DEFAULT NULL,
    `date_of_birth` VARCHAR(255) NULL DEFAULT NULL,
    `mail` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NULL DEFAULT NULL,
    `password` VARCHAR(255) NULL DEFAULT NULL,
    `role` VARCHAR(255) NULL DEFAULT NULL,
    `surname` VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id_user`),
    UNIQUE INDEX `UKkb57rl39lkfm8oxkj4ac78yku` (`mail` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;