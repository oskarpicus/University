create table Spectacole
(
    id    integer primary key autoincrement,
    data  date,
    titlu varchar(50),
    pret  real,
    sold  real
);

create table SpectacolLocuriVandute
(
    id_spectacol integer,
    nr_loc       integer,
    primary key (id_spectacol, nr_loc),
    foreign key (id_spectacol) references Spectacole (id)
);

create table Vanzari
(
    id                integer primary key autoincrement,
    id_spectacol      integer,
    data              date,
    nr_bilete_vandute int,
    suma              float,
    foreign key (id_spectacol) references Spectacole (id)
);

create table VanzariLocuri
(
    id_vanzare integer,
    nr_loc     integer,
    primary key (id_vanzare, nr_loc),
    foreign key (id_vanzare) references Vanzari (id)
);