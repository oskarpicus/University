create database tourismagencytest;

create table Users
(
    id bigint primary key generated by default as identity,
    username varchar(30),
    password varchar(30)
);

create table Trips
(
    id bigint primary key generated by default as identity,
    destination varchar(30),
    transportFirm varchar(30),
    departureTime timestamp,
    price float,
    seats int
);

create table Reservations
(
    id bigint primary key generated by default as identity ,
    client varchar(30),
    phoneNumber varchar(30),
    tickets int,
    tripId bigint,
    userId bigint,
    constraint fk_ReservationTrip foreign key(tripId) references Trips(id)
        on delete cascade ,
    constraint fk_ReservationUser foreign key(userId) references Users(id)
        on delete cascade
);

insert into Users(username, password) VALUES
('Mary','Poppins'),('Andrew','Right'),('John','John');

insert into Trips(destination, transportFirm, departureTime, price, seats) VALUES
('Cairo','A .SRL','2021-07-19 19:10:00',190,40),
('Cairo','A .SRL','2021-10-19 19:10:00',250,50),
('Bucharest','B .SRL','2021-05-20 20:00:00',50,20),
('Paris','C .SRL','2021-08-08 10:50:00',300,20);

insert into Reservations(client, phoneNumber, tickets, tripId, userId) VALUES
('Anne','072',2,1,1),('Bob','032',4,1,2),('John','098',2,2,1),('Anne','072',4,3,2),('Boris','080',4,2,2);