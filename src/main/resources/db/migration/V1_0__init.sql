CREATE TABLE `auth_user` (
  `id` BIGINT(6) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('SUPER ADMIN', 'ADMIN', 'USER') NOT NULL,
  `is_deleted` TINYINT(6) DEFAULT 0 NOT NULL ,
  `is_disabled` TINYINT(6) DEFAULT 0 NOT NULL,
  `created_on` DATETIME NOT NULL,
  `updated_on` DATETIME NULL,
  `last_logged_in_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
