 
 
--  Collation :  utf8_general_ci	

  
 create database if NOT EXISTS CategoryItems   CHARACTER SET utf8 COLLATE utf8_general_ci ;

use   CategoryItems ;
 

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

 
drop TABLE IF EXISTS categories ;
drop TABLE IF EXISTS items ;
drop TABLE IF EXISTS users ;


 
CREATE TABLE categories(
id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY ,
name text NOT NULL  
 );
 

CREATE TABLE items(
id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY  ,
name text NOT NULL   ,
description text NOT NULL  ,
 category_id bigint NOT NULL
);

CREATE TABLE `users` (
  user_id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  registeration_id text NOT NULL DEFAULT '' 
 ) ;

 
 INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'catname1'),
(2, 'catname2'),
(3, 'catname3'),
(4, 'catname4'),
(5, 'catname5'),
(6, 'catname6'),
(7, 'catname7'),
(8, 'catname8'),
(9, 'catname9'),
(10, 'catname10'),
(11, 'catname11');




INSERT INTO `items` (`id`, `name`, `description`, `category_id`) VALUES
(1, 'itemname1', 'itemdesc1', 5),
(2, 'itemname2', 'itemdesc2', 8),
(3, 'itemname3', 'itemdesc3', 5),
(4, 'itemname4', 'itemdesc4', 2);

 