DROP database IF EXISTS softeng2018;
CREATE DATABASE  IF NOT EXISTS `softeng2018` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `softeng2018`;

/*create table products*/
DROP TABLE IF EXISTS products;

CREATE TABLE products (id int(20) NOT NULL auto_increment,
                       name varchar(255) NOT NULL,
                       description mediumtext,
                       withdrawn bit(1) DEFAULT 0 NOT NULL,
                       `category` varchar(255) NOT NULL,
                       PRIMARY KEY (id),
                       INDEX(name)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table shops*/
DROP TABLE IF EXISTS shops;

CREATE TABLE shops (id int(20) NOT NULL auto_increment,
                    name varchar(255) NOT NULL,
                    address varchar(255) NOT NULL,
                    withdrawn bit(1) DEFAULT 0 NOT NULL,
                    coordinates POINT NOT NULL SRID 4326, SPATIAL INDEX(coordinates),
                    PRIMARY KEY (id),
                    INDEX(name)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table tags*/
-- DROP TABLE IF EXISTS tags;
--
-- CREATE TABLE tags (id int(20) NOT NULL auto_increment,
--                    tag_name varchar(255) NOT NULL,
--                    PRIMARY KEY (id),
--                    INDEX (tag_name)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table users*/
DROP TABLE IF EXISTS users;

CREATE TABLE users (id int(20) NOT NULL auto_increment,
                    username varchar(255) NOT NULL,
                    email varchar(255) NOT NULL,
                    password_hash varchar(255) NOT NULL UNIQUE,
                    enabled bit(1) DEFAULT 1,
                    admin bit(1) DEFAULT 0,
                    jwt varchar(255),
                    PRIMARY KEY (id),
                    UNIQUE KEY email_unique (email),
                    INDEX (username),
                    INDEX (jwt)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table prices*/
DROP TABLE IF EXISTS prices;

CREATE TABLE prices (id int(20) NOT NULL AUTO_INCREMENT,
                     price double NOT NULL,
                     `date` date NOT NULL,
                     product_id int(20) NOT NULL,
                     shop_id int(20) NOT NULL,
                     PRIMARY KEY (id, product_id, shop_id),
                     CONSTRAINT FK_prod FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE ON UPDATE CASCADE,
                     CONSTRAINT FK_shop FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE ON UPDATE CASCADE,
                     INDEX (product_id),
                     INDEX (shop_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table product_tag_map*/
DROP TABLE IF EXISTS product_tags;

CREATE TABLE `product_tags` (
  `product_id` int(20) NOT NULL DEFAULT '0',
  `tags` varchar(255) NOT NULL,
  PRIMARY KEY (`product_id`,`tags`),
  CONSTRAINT `fk_prod2432` FOREIGN KEY (`product_id`) REFERENCES products (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  INDEX(product_id), INDEX(tags)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*create table shop_tag_map*/
DROP TABLE IF EXISTS shop_tags;

CREATE TABLE `shop_tags` (
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `tags` varchar(255) NOT NULL,
  PRIMARY KEY (shop_id,tags),
  CONSTRAINT `fk_prod2456` FOREIGN KEY (`shop_id`) REFERENCES shops (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  INDEX(shop_id), INDEX(tags)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

