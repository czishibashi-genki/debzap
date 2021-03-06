-- MySQL Script generated by MySQL Workbench
-- Wed Nov 16 20:27:04 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema debzap
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema debzap
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `debzap` DEFAULT CHARACTER SET utf8 ;
USE `debzap` ;

-- -----------------------------------------------------
-- Table `debzap`.`site`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `debzap`.`site` (
  `id` INT(11) NOT NULL,
  `base_url` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `debzap`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `debzap`.`article` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `link` VARCHAR(1024) NULL DEFAULT NULL,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fav_count` INT(11) NULL DEFAULT NULL,
  `site_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `debzap`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `debzap`.`favorite` (
  `uuid` VARCHAR(255) NOT NULL,
  `site_id` INT NULL,
  `created_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
