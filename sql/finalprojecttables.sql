CREATE DATABASE FinalProject;

USE FinalProject;

CREATE TABLE Item
(
    ID int auto_increment, 
    ItemCode varchar(10) NOT NULL, 
    ItemDescription varchar(50),
    Price decimal(4,2) DEFAULT 0,
    primary key (ID),
    UNIQUE (ItemCode)
);

CREATE TABLE Purchase
(
    ID int auto_increment, 
    ItemID int NOT NULL,
    Quantity int NOT NULL,
    PurchaseDate datetime DEFAULT current_timestamp,
    primary key (ID),
    FOREIGN KEY (ItemID) REFERENCES Item(ID)
);

CREATE TABLE Shipment
(
    ID int auto_increment, 
    ItemID int NOT NULL,
    Quantity int NOT NULL,
    ShipmentDate date NOT NULL,
    primary key (ID),
    FOREIGN KEY (ItemID) REFERENCES Item (ID),
    UNIQUE (ShipmentDate)
);

DELIMITER //
CREATE PROCEDURE create_item(IN _ItemCode varchar(10), IN _ItemDescription varchar(50), IN _price double)
BEGIN
    INSERT INTO Item (ItemCode, ItemDescription, price) VALUES (_ItemCode, _ItemDescription, _price);
END //
DELIMITER ;
