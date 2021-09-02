create database tourismagency;

create table Users
(
    id bigint primary key generated always as identity,
    username varchar(30),
    password varchar(30)
);

create table Trips
(
  id bigint primary key generated always as identity,
  destination varchar(30),
  transportFirm varchar(30),
  departureTime timestamp ,
  price float,
  seats int
);

create table Reservations
(
  id bigint primary key generated always as identity ,
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