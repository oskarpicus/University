use Biblioteca;
go

go
create table LogTable
(
	id int primary key identity,
	nameOperation varchar(50),
	result varchar(20),
	date datetime default getdate()
);
-- pentru validare: validCarteAutor, validAutor, validCarte

go
create or alter procedure uspAddCarteAutorVar1
(@titlu varchar(50), @an int, @nume varchar(30), @prenume varchar(30), @tara varchar(30), @nrCarti int)
as begin
	set nocount on;
	declare @errorMessages varchar(150);
	declare @type varchar(2);
	set @type = 'C';
	declare @idCarte int;
	declare @idAutor int;

	begin tran
		begin try
			exec validCarte @titlu, @an, @errorMessages output;  -- validare Carte

			declare @data_imprumut date = getdate();
			declare @eid int = (select top 1 eid from Editura);
			declare @CNP_Imprumutator int = (select top 1 CNP from Imprumutator);
			declare @CNP_Angajat int = (select top 1 CNP_Angajat from Sala);

			insert into Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat)
			values (@titlu,@an,@data_imprumut,@eid,@CNP_Imprumutator,@CNP_Angajat);
			set @idCarte = @@IDENTITY;

			set @type = 'A';
			exec validAutor @nume, @prenume, @tara, @nrCarti, @errorMessages output;  -- validare Autor

			insert into Autor(nume, prenume ,tara, nrCarti)
			values (@nume, @prenume, @tara, @nrCarti);
			set @idAutor = @@IDENTITY;

			set @type = 'CA';
			exec validCarteAutor @idCarte, @idAutor, @errorMessages output;  -- validare CarteAutor

			insert into CarteAutor(cid, autorid)
			values (@idCarte, @idAutor);

			insert into LogTable(nameOperation, result)
			values ('All inserts', 'Commit');

			commit tran;
		end try

		begin catch
			rollback tran
			insert into LogTable(nameOperation, result)
			values (case @type 
						when 'C' then 'Insert Carte'
						when 'A' then 'Insert Autor'
						when 'CA' then 'Insert Carte Autor'
					end, 'Rollback');
		end catch
end;

select * from Carte;
select * from Autor;
select * from CarteAutor;

-- unsuccessfull
exec uspAddCarteAutorVar1 'Ana', -2000, '', 'r', 'Germania', -2;
exec uspAddCarteAutorVar1 'Ana', 2000, '', 'r', 'Germania', -2;
exec uspAddCarteAutorVar1 'Ana', -2000, 'Jeremy', 'John' , 'Romania', 4;

-- successfull
exec uspAddCarteAutorVar1 'Ana', 2000, 'Jeremy', 'John', 'Romania', 4;

select * from LogTable;

delete from Carte where titlu='Ana';
delete from Autor where nume='Jeremy';

----------

go
create or alter procedure uspAddCarteAutorVar2
(@titlu varchar(50), @an int, @nume varchar(30), @prenume varchar(30), @tara varchar(30), @nrCarti int)
as begin
	set nocount on;
	declare @errorMessages varchar(150);
	declare @idCarte int;
	declare @idAutor int;

	begin tran  -- insert Carte
		begin try
			exec validCarte @titlu, @an, @errorMessages output;  -- validare Carte

			declare @data_imprumut date = getdate();
			declare @eid int = (select top 1 eid from Editura);
			declare @CNP_Imprumutator int = (select top 1 CNP from Imprumutator);
			declare @CNP_Angajat int = (select top 1 CNP_Angajat from Sala);

			insert into Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat)
			values (@titlu,@an,@data_imprumut,@eid,@CNP_Imprumutator,@CNP_Angajat);
			set @idCarte = @@IDENTITY;

			insert into LogTable(nameOperation, result) values ('Insert Carte', 'Commit');
			commit tran;
		end try
		begin catch
			rollback tran;
			insert into LogTable(nameOperation, result) values ('Insert Carte', 'Rollback');
			return;
		end catch

	begin tran  -- insert Autor
		begin try
			exec validAutor @nume, @prenume, @tara, @nrCarti, @errorMessages output;  -- validare Autor

			insert into Autor(nume, prenume ,tara, nrCarti)
			values (@nume, @prenume, @tara, @nrCarti);
			set @idAutor = @@IDENTITY;

			insert into LogTable(nameOperation, result) values ('Insert Autor', 'Commit');
			commit tran;
		end try
		begin catch
			rollback tran;
			insert into LogTable(nameOperation, result) values ('Insert Autor', 'Rollback');
			return;
		end catch

	begin tran  -- insert CarteAutor
		begin try
			exec validCarteAutor @idCarte, @idAutor, @errorMessages output;  -- validare CarteAutor

			insert into CarteAutor(cid, autorid)
			values (@idCarte, @idAutor);

			insert into LogTable(nameOperation, result) values ('Insert CarteAutor', 'Commit');
			commit;
		end try
		begin catch
			rollback tran;
			insert into LogTable(nameOperation, result) values ('Insert CarteAutor', 'Rollback');
			return;
		end catch
end;

select * from Carte;
select * from Autor;
select * from CarteAutor;
select * from LogTable;

-- unsuccessfull
exec uspAddCarteAutorVar2 'Maria', -2010, 'Ion', 'Pop', 'Germania', 20;
exec uspAddCarteAutorVar2 'Maria', 2010, 'Ion', 'Pop', 'Germania', -20; 

-- successfull
exec uspAddCarteAutorVar2 'Maria', 2010, 'Ion', 'Pop', 'Germania', 20;

delete from Carte where titlu = 'Maria';
delete from Autor where nume = 'Ion' and prenume = 'Pop';