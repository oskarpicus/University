CREATE DATABASE Biblioteca
go
USE Biblioteca
go

-- Un angajat locuieste la o adresa, iar la o adresa pot sa locuiasca mai multi angajati ==> relatie 1-n

CREATE TABLE Adresa
(
	aid INT PRIMARY KEY,
	nume VARCHAR(30) NOT NULL,
	numar INT,
	oras VARCHAR(30),
	judet VARCHAR(30),
	bloc VARCHAR(30),
	apartament INT
);

CREATE TABLE Angajat
(
	CNP INT,
	aid INT,
	nume VARCHAR(30) NOT NULL,
	prenume VARCHAR(30),
	salariu FLOAT
	CONSTRAINT pk_Angajat PRIMARY KEY(CNP),
	CONSTRAINT fk_Angajat_Adresa FOREIGN KEY(aid) REFERENCES Adresa(aid)
);

-- Un angajat are arondata o sala, o sala este supravegheata de un angajat ==> relatie 1-1

CREATE TABLE Sala
(
	denumire VARCHAR(10) NOT NULL, -- ex. sala II/I
	cladire VARCHAR(30),
	CNP_Angajat INT FOREIGN KEY REFERENCES Angajat(CNP)
	CONSTRAINT pk_Sala PRIMARY KEY(CNP_Angajat)
);

CREATE TABLE Editura
(
	eid INT PRIMARY KEY IDENTITY,
	nume VARCHAR(30) NOT NULL,
	oras VARCHAR(30),
	nrPublicatii INT CHECK(nrPublicatii>0)
);

CREATE TABLE Imprumutator
(
	CNP INT PRIMARY KEY,
	nume VARCHAR(30) NOT NULL,
	prenume VARCHAR(30),
	varsta INT CHECK(varsta>10)
);

-- O carte este publicata de o editura, iar o editura poate sa publice mai multe carti ==> relatie 1-n
-- O carte este imprumutata de un imprumutator, iar un imprumutator poate sa imprumute mai multe carti ==> relatie 1-n

CREATE TABLE Carte
(
	cid INT PRIMARY KEY IDENTITY,
	titlu VARCHAR(50) NOT NULL,
	an INT,
	data_imprumut DATE DEFAULT NULL,
	eid INT FOREIGN KEY REFERENCES Editura(eid),
	CNP_Imprumutator INT,
	CNP_Angajat INT FOREIGN KEY REFERENCES Sala(CNP_Angajat),
	CONSTRAINT fk_Carte_Imprumutator FOREIGN KEY(CNP_Imprumutator) REFERENCES Imprumutator(CNP)
);

-- O carte are mai multe teme, iar o tema poate sa apara la mai multe carti ==> relatie m-n

CREATE TABLE Tema -- genre ex. razboi, iubire
(
	tid INT PRIMARY KEY IDENTITY,
	denumire VARCHAR(30) NOT NULL,
	curent VARCHAR(30) -- curent literar
);

CREATE TABLE CarteTema
(
	cid INT FOREIGN KEY REFERENCES Carte(cid),
	tid INT FOREIGN KEY REFERENCES Tema(tid),	
	CONSTRAINT pk_CarteTema PRIMARY KEY(cid,tid) 
);

-- O carte poate fi scrisa de mai multi autori, iar un autor poate avea mai multe carti scrise ==> relatie m-n

CREATE TABLE Autor
(
	autorid INT IDENTITY PRIMARY KEY,
	nume VARCHAR(30) NOT NULL,
	prenume VARCHAR(30) NOT NULL,
	tara VARCHAR(30),
	nrCarti INT CHECK(nrCarti>0)
);

CREATE TABLE CarteAutor
(
	cid INT FOREIGN KEY REFERENCES Carte(cid),
	autorid INT FOREIGN KEY REFERENCES Autor(autorid),
	CONSTRAINT pk_CarteAutor PRIMARY KEY(cid,autorid)
);
