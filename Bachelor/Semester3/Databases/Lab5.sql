use Biblioteca;
go

-- tabelele considerate: Autor, CarteAutor, Carte

create or alter view View_1 as --autorii care au carti in biblioteca
	select distinct a.nume,a.prenume
	from Autor a inner join CarteAutor ca on a.autorid=ca.autorid inner join Carte c on ca.cid=c.cid
go

create or alter view View_2 as -- autorii cu carti in biblioteca, impreuna cu numarul de carti ale acestora
								-- ale caror nume incep cu C
	select a.nume,a.prenume,count(*) [Nr Carti]
	from Autor a inner join CarteAutor ca on a.autorid=ca.autorid inner join Carte c on ca.cid=c.cid
	where a.nume like 'C%'
	group by a.nume,a.prenume
go

create index IX_CarteAutor_fks on CarteAutor(autorid asc,cid asc);
create index IX_Autor_nume_prenume on Autor(nume asc, prenume asc);

alter index IX_CarteAutor_fks on CarteAutor disable;
alter index IX_CarteAutor_fks on CarteAutor rebuild;
alter index IX_Autor_nume_prenume on Autor disable;
alter index IX_Autor_nume_prenume on Autor rebuild;

select * from View_1;
select * from View_2;

-----------------------------------------------------
-- functiile intorc valoarea 0, daca este valid, 1, altfel

go
create or alter function ufValidCarteAutorPKs
(@cid int, @autorid int)
returns int as
begin
	if(not exists (select * from Carte where Carte.cid=@cid))
		return 1;
	if(not exists(select * from Autor where Autor.autorid=@autorid))
		return 1;
	return 0;
end;

go
create or alter function ufValidCarteAutorCombination
(@cid int,@autorid int)
returns bit as
begin
	if(exists (select * from CarteAutor ca where ca.autorid=@autorid and ca.cid=@cid))
		return 1;
	return 0;
end;

go
create or alter procedure validCarteAutor
(@cid int, @autorid int, @errorMessages varchar(50) output)
as begin
	set @errorMessages='';
	if(dbo.ufValidCarteAutorPKs(@cid,@autorid)=1)
		set @errorMessages = @errorMessages + 'This data does not refer other records '+char(10);
	if(dbo.ufValidCarteAutorCombination(@cid,@autorid)=1)
		set @errorMessages = @errorMessages + 'You already added this record'+char(10);
	if(@errorMessages!='')
		throw 50002,@errorMessages,1;
end;
go

go
create or alter procedure CreateCarteAutor
(@cid int, @autorid int)
as begin
	set nocount on;
	declare @errorMessages varchar(50);
	exec validCarteAutor @cid,@autorid,@errorMessages;

	insert into CarteAutor(cid,autorid)
	values (@cid,@autorid);
	print 'Successfully added the record';
	print 'CREATE in CarteAutor completed';
end;

go
create or alter procedure ReadCarteAutor
(@cid int, @autorid int)
as begin
	set nocount on;
	select * from CarteAutor where cid=@cid and autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	print 'READ in CarteAutor completed';
end;

go
create or alter procedure DeleteCarteAutor
(@cid int, @autorid int)
as begin
	set nocount on;
	delete from CarteAutor where cid=@cid and autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else
		print 'Successfully deleted the record';
	print 'DELETE in CarteAutor completed';
end;

go
create or alter procedure UpdateCarteAutor
(@cid int,@autorid int,@cidNew int,@autoridNew int)
as begin
	set nocount on;
	declare @errorMessages varchar(50);
	exec validCarteAutor @cidNew, @autoridNew, @errorMessages;
	
	update CarteAutor
	set cid=@cidNew, autorid=@autoridNew
	where cid=@cid and autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else
		print 'Successfully updated the record';
	print 'UPDATE in CarteAutor completed';
end;

-------------------------------------------------------------
go
create or alter function ufValidAutorNume
(@nume varchar(30))
returns int as
begin
	if(@nume='') return 1;
	return 0;
end;

go
create or alter function ufValidAutorPrenume
(@prenume varchar(30))
returns int as
begin
	if(@prenume='') return 1;
	return 0;
end;

go
create or alter function ufValidAutorTara
(@tara varchar(30))
returns int as
begin
	if(@tara='') return 1;
	return 0;
end;

go
create or alter function ufValidAutorNrCarti
(@nrcarti int)
returns int as begin
	if(@nrcarti>0) return 0;
	return 1;
end;

go
create or alter procedure validAutor
(@nume varchar(30), @prenume varchar(30), @tara varchar(30), @nrCarti int, @errorMessages varchar(150) output)
as begin
	set @errorMessages = '';
	if(dbo.ufValidAutorNume(@nume)=1)
		set @errorMessages= @errorMessages+'Invalid author last name'+CHAR(10);
	if(dbo.ufValidAutorPrenume(@prenume)=1)
		set @errorMessages=@errorMessages+'Invalid author first name'+CHAR(10);
	if(dbo.ufValidAutorTara(@tara)=1)
		set @errorMessages=@errorMessages+'Invalid author country'+CHAR(10);
	if(dbo.ufValidAutorNrCarti(@nrCarti)=1)
		set @errorMessages=@errorMessages+'Invalid number of books written'+CHAR(10);
	if(@errorMessages!='')
		throw 50002,@errorMessages,1;
end;

go
create or alter procedure CreateAutor
(@nume varchar(30), @prenume varchar(30), @tara varchar(30), @nrCarti int)
as begin
	set nocount on;
	declare @errorMessages varchar(150);
	exec validAutor @nume,@prenume,@tara,@nrCarti,@errorMessages;

	insert into Autor(nume,prenume,tara,nrCarti) -- p.k. este identity
	values (@nume,@prenume,@tara,@nrCarti);
	print 'Successfully added the record';
	print 'CREATE in Autor completed';
end;

go
create or alter procedure ReadAutor
(@autorid int)
as begin
	set nocount on;
	select * from Autor where autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	print 'READ in Autor completed';
end;

go
create or alter procedure DeleteAutor
(@autorid int)
as begin
	set nocount on;

	if(exists (select * from CarteAutor where autorid=@autorid))
	begin
		print 'Cannot delete a referenced record';
		return;
	end;

	delete from Autor where autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else
		print 'Successfully deleted the record';
	print 'DELETE in Autor completed';
end;

go
create or alter procedure UpdateAutor
(@autorid int,@numeNew varchar(30),@prenumeNew varchar(30),@taraNew varchar(30),@nrCartiNew varchar(30))
as begin
	set nocount on;
	declare @errorMessages varchar(150);
	exec validAutor @numeNew,@prenumeNew,@taraNew,@nrCartiNew, @errorMessages;

	update Autor 
	set nume=@numeNew, prenume=@prenumeNew, tara=@taraNew, nrCarti=@nrCartiNew
	where autorid=@autorid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else
		print 'Successfully updated the record';
	print 'UPDATE in Autor completed';
end;

------------------------------------------------------------------------
go
create or alter function ufValidCarteTitlu
(@titlu varchar(50))
returns int as begin
	if(@titlu='') return 1;
	return 0;
end;

go
create or alter function ufValidCarteAn
(@an int) 
returns int as begin
	if(@an>0) return 0;
	return 1;
end;

go
create or alter procedure validCarte
(@titlu varchar(50), @an int, @errorMessages varchar(50) output)
as begin
	set @errorMessages = '';
	if(dbo.ufValidCarteTitlu(@titlu)=1)
		set @errorMessages = @errorMessages + 'Invalid book title'+CHAR(10);
	if(dbo.ufValidCarteAn(@an)=1)
		set @errorMessages = @errorMessages + 'Invalid book year'+char(10);
	if(@errorMessages!='')
		throw 50002, @errorMessages, 1;
end;

go
create or alter procedure CreateCarte
(@titlu varchar(50), @an int)
as begin
	set nocount on;
	declare @errorMessages varchar(50);
	exec validCarte @titlu, @an, @errorMessages;

	declare @data_imprumut date = getdate();
	declare @eid int = (select top 1 eid from Editura);
	declare @CNP_Imprumutator int = (select top 1 CNP from Imprumutator);
	declare @CNP_Angajat int = (select top 1 CNP_Angajat from Sala);

	insert into Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat) -- p.k. este identity
	values (@titlu,@an,@data_imprumut,@eid,@CNP_Imprumutator,@CNP_Angajat);
	print 'Successfully added the record';
	print 'CREATE in Carte completed';
end;

go
create or alter procedure ReadCarte
(@cid int)
as begin
	set nocount on;
	select * from Carte where cid=@cid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	print 'READ in Carte completed'; 
end;

go
create or alter procedure DeleteCarte
(@cid int)
as begin
	set nocount on;

	if(exists (select * from CarteAutor where cid=@cid))
	begin
		print 'Cannot delete a referenced record';
		return;
	end;

	delete from Carte where cid=@cid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else print 'Successfully deleted the record';
	print 'DELETE in Carte completed';
end;

go
create or alter procedure UpdateCarte
(@cid int, @titluNew varchar(50), @anNew int)
as begin
	set nocount on;
	declare @error varchar(50);
	exec validCarte @titluNew, @anNew, @error;

	update Carte
	set titlu=@titluNew, an=@anNew
	where cid = @cid;
	if(@@ROWCOUNT=0)
		print 'There is no such record';
	else print 'Successfully updated the record';
	print 'UPDATE in Carte completed';
end;


exec CreateCarteAutor 39,14;
exec DeleteCarteAutor 39,14;
exec UpdateCarteAutor 24,14,39,14;
exec ReadCarteAutor 39,14;
select * from CarteAutor;

exec CreateCarte 'X',32;
exec ReadCarte 39;
exec DeleteCarte 39;
exec UpdateCarte 39,'Y',44;
select * from Carte;

exec CreateAutor 'A','A','A',4;
exec UpdateAutor 14,'B','B','B',37;
exec DeleteAutor 14;
exec ReadAutor 14;
select * from Autor;