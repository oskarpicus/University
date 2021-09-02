CREATE TABLE Routes
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    departure VARCHAR(50),
    arrival VARCHAR(50)
);

insert into Routes(departure, arrival)
values ('Bucharest', 'Sofia'), ('Kiev', 'Bucharest'), ('Bucharest', 'Prague'),
       ('Berlin', 'Paris'), ('Paris', 'Rotterdam'), ('Berlin', 'Prague'),
       ('London', 'Paris'), ('Paris', 'London'), ('Paris', 'Stuttgart'),
       ('Berlin', 'Rome'), ('Bucharest', 'Budapest');

CREATE TABLE Persons
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName varchar(50),
    lastName varchar(50),
    phoneNumber varchar(15),
    email varchar(50)
);

insert into Persons(firstName, lastName, phoneNumber, email)
values ('Ion', 'Pop', '0123', 'ion.pop@yahoo.com'),
       ('Olivia', 'Jacob', '92042', 'olivia.jacob@gmail.com');

insert into Persons(firstName, lastName, phoneNumber, email)
values ('Henry', 'Cornwall', '4242', 'henry.c@yahoo.com'),
       ('Evelyn', 'Lucas', '12042', 'eve_l@hotmail.com'),
       ('Benjamin', 'Jon', '029323', 'ben.j.1@yahoo.com'),
       ('William', 'Windsor', '104242', 'will_wind@gmail.com');

insert into Persons(firstName, lastName, phoneNumber, email)
values ('Ramona', 'Andrew', '1924924', 'ram_a@gmail.com');

CREATE TABLE Games
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(50),
    brand varchar(50),
    platform varchar(50),
    genre varchar(50),
    price float
);

insert into Games(name, brand, platform, genre, price)
VALUES ('Fallout4', 'Bethesda', 'PS4', 'Action', 100),
       ('Spider-Man', 'Insomniac', 'PS4', 'Action' , 180),
       ('Doom Eternal', 'Bethesda', 'PS4', 'Action' , 100),
       ('New Super Mario Bros U', 'Nintendo', 'Switch', 'Adventure', 240),
       ('The Legend of Zelda', 'Nintendo', 'Switch', 'Adventure', 300),
       ('Ratchet and Clank', 'Insomniac', 'PS4', 'Adventure', 120),
       ('Pokemon', 'Nintendo', 'Switch' , 'Adventure', 250),
       ('Spyro the Dragon', 'Insomniac', 'PS4', 'Adventure', 140),
       ('Spyro the Dragon', 'Insomniac', 'Switch', 'Adventure', 140);