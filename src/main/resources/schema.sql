DROP SCHEMA IF EXISTS `bot`;

CREATE SCHEMA IF NOT EXISTS `bot`;

USE `bot`;

CREATE TABLE `city` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `city` VARCHAR(255) NOT NULL
);

CREATE TABLE `place` (
	`id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `place` VARCHAR(255) DEFAULT NULL,

    `fk_city_id` INTEGER DEFAULT NULL,

	CONSTRAINT `fk_place_x_city` FOREIGN KEY (`fk_city_id`) REFERENCES `city` (`id`)
		ON DELETE CASCADE
);

INSERT INTO `city` (`city`) VALUES ('Moscow');
INSERT INTO `city` (`city`) VALUES ('Minsk');


INSERT INTO `place` (`place`, `fk_city_id`) VALUES ('Do not forget to visit Red Square. You do not have to go)))', 1);
INSERT INTO `place` (`place`, `fk_city_id`) VALUES ('Do not forget to visit the library', 2);
