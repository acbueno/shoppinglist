CREATE DATABASE IF NOT EXISTS shoplist;

USE shoplist;

CREATE TABLE list_shop (
   id_list int NOT NULL AUTO_INCREMENT,
   name varchar(255),
   list_date date,
   PRIMARY KEY(id_list)
); 
 

   
CREATE TABLE product (
    id_product int NOT NULL AUTO_INCREMENT,
    product_name varchar(255),
    qtd int(10),
    date_buy date,
    id_list int,
    PRIMARY KEY(id_product),
    FOREIGN KEY (id_list) REFERENCES list_shop(id_list)); 
 
