-- Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
--
-- You may not modify, use, reproduce, or distribute this software
-- except in compliance with the terms of the license at:
-- http://developer.sun.com/berkeley_license.html
--
-- author: tgiunipero
--

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `simpleaffablebean` ;
CREATE SCHEMA IF NOT EXISTS `simpleaffablebean` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `simpleaffablebean` ;

-- -----------------------------------------------------
-- Table `simpleaffablebean`.`customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`customer` ;

CREATE  TABLE IF NOT EXISTS `simpleaffablebean`.`customer` (
  `customer_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `phone` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NOT NULL ,
  `city_region` VARCHAR(2) NOT NULL ,
  `cc_number` VARCHAR(19) NOT NULL ,
  PRIMARY KEY (`customer_id`) )
ENGINE = InnoDB
COMMENT = 'maintains customer details';


-- -----------------------------------------------------
-- Table `simpleaffablebean`.`customer_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`customer_order` ;

CREATE  TABLE IF NOT EXISTS `simpleaffablebean`.`customer_order` (
  `customer_order_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `customer_id` INT UNSIGNED NOT NULL ,
  `amount` INT UNSIGNED NOT NULL ,
  `date_created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `confirmation_number` INT UNSIGNED NOT NULL ,
  PRIMARY KEY (`customer_order_id`) ,
  INDEX `fk_customer_order_customer` (`customer_id` ASC) ,
  CONSTRAINT `fk_customer_order_customer`
    FOREIGN KEY (`customer_id` )
    REFERENCES `simpleaffablebean`.`customer` (`customer_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'maintains customer order details';


-- -----------------------------------------------------
-- Table `simpleaffablebean`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`category` ;

CREATE  TABLE IF NOT EXISTS `simpleaffablebean`.`category` (
  `category_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`category_id`) )
ENGINE = InnoDB
COMMENT = 'contains product categories, e.g., dairy, meats, etc.';


-- -----------------------------------------------------
-- Table `simpleaffablebean`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`product` ;

CREATE TABLE IF NOT EXISTS `simpleaffablebean`.`product` (
  `product_id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `category_id` TINYINT UNSIGNED NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `price` INT UNSIGNED NOT NULL ,

  -- Delete after description is moved to resource bundle
  `description` TINYTEXT NULL ,

  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  PRIMARY KEY (`product_id`) ,
  INDEX `fk_product_category` (`category_id` ASC) ,
  CONSTRAINT `fk_product_category`
    FOREIGN KEY (`category_id` )
    REFERENCES `simpleaffablebean`.`category` (`category_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'contains product details';


-- -----------------------------------------------------
-- Table `simpleaffablebean`.`customer_order_line_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `simpleaffablebean`.`customer_order_line_item` ;

CREATE TABLE IF NOT EXISTS `simpleaffablebean`.`customer_order_line_item` (
  `customer_order_id` INT UNSIGNED NOT NULL ,
  `product_id` INT UNSIGNED NOT NULL ,
  `quantity` SMALLINT UNSIGNED NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`customer_order_id`, `product_id`) ,
  INDEX `fk_customer_order_line_item_customer_order` (`customer_order_id` ASC) ,
  INDEX `fk_customer_order_line_item_product` (`product_id` ASC) ,
  CONSTRAINT `fk_customer_order_line_item_customer_order`
    FOREIGN KEY (`customer_order_id` )
    REFERENCES `simpleaffablebean`.`customer_order` (`customer_order_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ordered_product_product`
    FOREIGN KEY (`product_id` )
    REFERENCES `simpleaffablebean`.`product` (`product_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'matches products with customer orders and records their quantity';



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
