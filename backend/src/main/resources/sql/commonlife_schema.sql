-- TEST SCHEMA --
SET @database_name = COMMONLIFE_DEV_YKIM

DROP DATABASE IF EXISTS @database_name
CREATE DATABASE @database_name
    CHARACTER SET = 'utf8'
    COLLATE = 'utf8_general_ci';

USE @database_name;

CREATE TABLE EXAMPLE( id int NOT NULL AUTO_INCREMENT, name VARCHAR(100), primary key(id) );
CREATE UNIQUE INDEX `name_UNIQUE` on EXAMPLE (`name` ASC);
INSERT INTO EXAMPLE VALUES ('test1');
INSERT INTO EXAMPLE VALUES ('test2');