CREATE DATABASE IF NOT EXISTS SHOPLIST;

USE SHOPLIST;

CREATE TABLE LIST (
   id INT AUTO_INCREMENT,
   name varchar(255),
   PRIMARY KEY(id)
); ENGINE=InnoDB DEFAULT CHARSET=utf8
   

CREATE TABLE PRODUCT (
    id INT AUTO_INCREMENT,
    product_name varchar(255),
    qtd int(10),
    date_buy date,
    id_list int,
    PRIMARY KEY(id),
    FOREIGN KEY (id_list) REFERENCES LIST(id)
);ENGINE=InnoDB DEFAULT CHARSET=utf8
