create table Trains
(
  id int primary key auto_increment,
  type varchar(50),
  departure varchar(50),
  arrival varchar(50),
  departureTime time,
  arrivalTime time
);

insert into Trains(type, departure, arrival, departureTime, arrivalTime)
values ('Short Distance', 'Deva', 'Simeria', '12:00:00', '12:30:00'),
       ('Long Distance', 'Simeria', 'Sibiu', '14:20:00', '17:00:00'),
       ('Long Distance', 'Cluj-Napoca', 'Deva', '08:00:00', '10:10:00'),
       ('Short Distance', 'Deva', 'Simeria', '08:00:00', '08:30:00'),
       ('Long Distance', 'Simeria', 'Sibiu', '08:00:00', '11:15:00'),
       ('Long Distance', 'Simeria', 'Sibiu', '19:00:00', '21:00:00');

create table Users
(
  id int primary key auto_increment,
  email varchar(50),
  password varchar(255)
);

create table Images
(
  id int primary key auto_increment,
  path varchar(50)
);

create table UsersImages
(
  idUser int,
  idImage int,
  constraint pk_UsersImages primary key (idUser, idImage),
  constraint fk_Users foreign key (idUser) references Users(id),
  constraint fk_Images foreign key (idImage) references Images(id)
);