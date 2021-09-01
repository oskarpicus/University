USE Biblioteca
go

INSERT Adresa
VALUES (1,'George Enescu',2,'Cluj-Napoca','CJ','3',18),
(2,'Ion Creanga',5,'Cluj-Napoca','CJ','10',10),
(3,'Revolutiei',10,'Cluj-Napoca','CJ','19',17);

INSERT Adresa(aid,nume,numar,oras,judet,bloc,apartament)
VALUES (4,'Florilor',19,'Sibiu','SB','K1',4),
(5,'Eroilor',2,'Hunedoara','HD','M5',20);

select * from Adresa

INSERT Angajat(CNP,aid,nume,prenume,salariu)
VALUES (1234,2,'Popescu','Ion',1500),
(8888,4,'Dumitru','Ana',2000),
(9204,4,'Dumitru','Petre',2450),
(1204,1,'Stan','Mircea',1800),
(4292,1,'Dumitru','Ionut',1900);

select * from Angajat

INSERT Sala(CNP_Angajat,denumire,cladire)
VALUES (1204,'5/I','Centrala'),
(9204,'C306','FSEGA'),
(8888,'N. Iorga','Centrala'),
(4292,'6/II','Centrala');

select * from Sala

INSERT Editura(nume,oras,nrPublicatii)
VALUES ('Humanitas','Bucuresti',900),
('Trei','Bucuresti',340),
('Penguin Readers','Londra',3000),
('Cartex','Cluj-Napoca',700),
('Herra','Sibiu',900),
('Steaua Nordului','Oradea',500),
('Adevarul','Bucuresti',920),
('Gramar','Bucuresti',250),
('Litera','Cluj-Napoca',400),
('Egmont','Bucuresti',390);

select * from Editura

INSERT Imprumutator(CNP,nume,prenume,varsta)
VALUES (4000,'Munteanu','Andrei',20),
(1204,'Stan','Mircea',43),
(9120,'Matei','Laura',35),
(1251,'Stefan','Iulia',15),
(9210,'Moldovan','Sergiu',28),
(4124,'Vasile','Patricia',45),
(5050,'Ciobanu','Razvan',34);

INSERT Imprumutator(CNP,nume,prenume,varsta)
VALUES (8888,'Dumitru','Ana',34),
(4292,'Dumitru','Ionut',40);

select * from Imprumutator

INSERT Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat)
VALUES ('Ion',1920,'20200712',4,1251,8888),
('D-l Goe',1875,NULL,3,NULL,8888),
('Bubico',1890,'20191219',4,5050,9204),
('Proces Verbal',1886,'20200808',5,5050,4292),
('O Scrisoare Pierduta',1886,'20200728',1,5050,8888),
('Zeii',1980,NULL,9,NULL,4292),
('Eroii',1980,NULL,9,NULL,4292),
('Padurea Spanzuratilor',1930,'20180917',7,4124,1204),
('Ciuleandra',1935,NULL,7,NULL,1204),
('Will Grayson Will Grayson',2010,NULL,3,NULL,1204),
('Paper Towns',2012,'20201024',3,1251,1204),
('Looking for Alaska',2012,'20201024',3,1251,1204);

INSERT Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat)
VALUES ('Fram Ursul Polar',1920,'20200410',6,8888,4292),
('Testament',1930,'20200720',3,4292,8888);

select * from Carte

INSERT Tema(denumire,curent)
VALUES ('iubire','romantism'),
('banul','realism'),
('familia','realism'),
('moartea','romantism'),
('razboi','realism'),
('satira','clasicism'),
('istorie',NULL),
('boala','naturalism'),
('teen fiction',NULL);

select * from Tema

INSERT CarteTema(cid,tid)
VALUES (24,1),(24,2),(31,5),(31,7),(25,6),(26,6),(29,7),(34,9),(35,9),(33,9),(27,6);

INSERT CarteTema(cid,tid)
VALUES (28,6),(30,7);

INSERT Autor(nume,prenume,tara,nrCarti)
VALUES ('Caragiale','Ion Luca','Romania',400),
('Rebreanu','Liviu','Romania',230),
('Mitru','Alexandru','Romania',100),
('Spyri','Johanna','Elvetia',90),
('Green','John','SUA',120),
('Levithan','David','SUA',50),
('Rowling','J.K.','Marea Britania',90),
('Blaga','Lucian','Romania',120),
('Arghezi','Tudor','Romania',170),
('Slavici','Ioan','Romania',200);

INSERT Autor(nume,prenume,tara,nrCarti)
VALUES ('Petrescu','Cezar','Romania',80);

select * from Autor

INSERT CarteAutor(cid,autorid)
VALUES (24,2),(31,2),(32,2),(33,5),(33,6),(34,5),(35,5),(28,1),(26,1),(29,3),(30,3);

INSERT CarteAutor(cid,autorid)
VALUES (36,11),(37,9)
