use Biblioteca2
go

-- adaugam tabelele de testare
insert into Tables(Name)
values ('Autor'), -- 1 PK no FK
('Carte'), -- 1 PK + FK
('CarteAutor'); -- 2 PK
go

-- creare de view-uri
create or alter view View_1 as
	select * from Autor
go

create or alter view View_2 as --autorii care au carti in biblioteca
	select distinct a.nume,a.prenume
	from Autor a inner join CarteAutor ca on a.autorid=ca.autorid inner join Carte c on ca.cid=c.cid
go

create or alter view View_3 as -- autorii cu carti in biblioteca, impreuna cu numarul de carti ale acestora
	select a.nume,a.prenume,count(*) [Nr Carti]
	from Autor a inner join CarteAutor ca on a.autorid=ca.autorid inner join Carte c on ca.cid=c.cid
	group by a.nume,a.prenume
go

-- adaugam view-urile de testare
insert into Views(Name)
values ('View_1'),('View_2'),('View_3');

-- adaugam numele testelor
insert into Tests(Name)
values ('delete_table'),('insert_table'),('select_view');

-- completam TestViews
insert into TestViews(TestID,ViewID)
values (3,1),(3,2),(3,3);

-- completam TestTables
insert into TestTables(TestID,TableID,NoOfRows,Position)
values (1,1,0,3),(1,2,0,2),(1,3,0,1), --teste de stergere
		(2,1,1000,1),(2,2,1000,2),(2,3,1000,3) --teste de inserare

-- stergere din primul tabel - CarteAutor
go
create or alter procedure delete1
as begin
	delete from CarteAutor;
	print 'S-a sters din CarteAutor';
end;

-- stergere din al doilea tabel - Carte
go
create or alter procedure delete2
as begin
	exec delete1;
	delete from Carte;
	print 'S-a sters din Carte';
end;

-- stergere din al treilea tabel - Autor
go
create or alter procedure delete3
as begin
	exec delete1;
	delete from Autor;
	print 'S-a sters din Autor';
end;

-- inserare in primul tabel - Autor
go
create or alter procedure insert1
as begin
	set nocount on;
	declare @nr_inreg_de_inserat int;
	declare @i int;
	set @i = 1;

	select top 1 @nr_inreg_de_inserat = tt.NoOfRows
	from TestTables tt
	where tt.Position=1 and tt.TestID=2;

	while @i < @nr_inreg_de_inserat
	begin
		declare @nr varchar(5) = convert(varchar(5),@i);
		declare @nume varchar(30) = 'Nume nr. '+@nr;
		declare @prenume varchar(30) = 'Prenume nr. '+@nr;
		declare @tara varchar(30) = 'Tara nr. '+@nr;

		insert into Autor(nume,prenume,tara,nrCarti)
		values (@nume,@prenume,@tara,@i);
		
		set @i=@i+1;
	end
	print 'S-a inserat in Autor';
end;

-- inserare in al doilea tabel - Carte
go
create or alter procedure insert2
as begin
	set nocount on;
	declare @nr_inreg_de_inserat int;
	declare @i int = 1;

	select top 1 @nr_inreg_de_inserat = tt.NoOfRows
	from TestTables tt
	where tt.Position=2 and tt.TestID=2;

	declare @eid int = (select top 1 eid from Editura);
	declare @CNP_Angajat int = (select top 1 CNP_Angajat from Sala);
	declare @CNP_Imprumutator int = (select top 1 CNP from Imprumutator);
	declare @data_imprumut date = '2020-12-03';

	while @i < @nr_inreg_de_inserat
	begin
		declare @titlu varchar(50) = 'Titlu nr. '+convert(varchar(5),@i);

		insert into Carte(titlu,an,data_imprumut,eid,CNP_Imprumutator,CNP_Angajat)
		values (@titlu,@i,@data_imprumut,@eid,@CNP_Imprumutator,@CNP_Angajat);

		set @i=@i+1;
	end
	print 'S-a inserat in Carte';
end;

-- inserare in al 3-lea tabel - CarteAutor
go
create or alter procedure insert3
as begin
	set nocount on;
	declare @nr_inreg_de_inserat int;
	declare @i int = 1;

	select top 1 @nr_inreg_de_inserat = tt.NoOfRows
	from TestTables tt
	where tt.Position=3 and tt.TestID=2;

	declare cursorAutor cursor fast_forward for
		select autorid from Autor;

	declare cursorCarte cursor fast_forward for
		select cid from Carte;

	open cursorAutor;

	declare @id_autor_curent int;
	declare @id_carte_curent int;
	fetch next from cursorAutor into @id_autor_curent;

	while @i<@nr_inreg_de_inserat and @@FETCH_STATUS=0
	begin
		open cursorCarte;
		fetch next from cursorCarte into @id_carte_curent;
		while @i<@nr_inreg_de_inserat and @@FETCH_STATUS=0
		begin
			insert into CarteAutor(cid,autorid)
			values (@id_carte_curent,@id_autor_curent);
			set @i = @i+1;
			fetch next from cursorCarte into @id_carte_curent;
		end
		close cursorCarte;

		if @i = 0 --daca Carte nu are nicio inregistrare
			break;

		fetch next from cursorAutor into @id_autor_curent;
	end
	print 'S-a inserat in CarteAutor';
	close cursorAutor;
	deallocate cursorAutor;
	deallocate cursorCarte;
end;

-- teste pentru View-uri
go
create or alter procedure select1
as begin
	select * from View_1;
end

go
create or alter procedure select2
as begin
	select * from View_2;
end

go
create or alter procedure select3
as begin
	select * from View_3;
end
-- procedura principala
go
create or alter procedure test
(@numar int) -- numarul indica id-ul tabelului si al view-ului
as begin
	declare @start datetime;
	declare @intermediate datetime;
	declare @finish datetime;

	declare @nr varchar(5) = convert(varchar(5),@numar);
	declare @procedure_select varchar(15) = 'select'+@nr;
	set @nr = (select top 1 Position from TestTables where TestID=2 and TableID=@numar); -- inserarea are codul 2
	declare @procedure_insert varchar(15) = 'insert'+@nr;
	set @nr = (select top 1 Position from TestTables where TestID=1 and TableID=@numar); -- stergerea are codul 1
	declare @procedure_delete varchar(15) = 'delete'+@nr;
	set @nr = convert(varchar(5),@numar);

	set @start=GETDATE();
	exec @procedure_delete;
	exec @procedure_insert;
	set @intermediate=GETDATE();
	exec @procedure_select;
	set @finish=GETDATE();
	Print DATEDIFF(ms,@start,@finish); -- ms = milisecunde

	set nocount on;
	insert into TestRuns(Description,StartAt,EndAt)
	values ('Test pentru tabela si view-ul '+@nr,@start,@finish);

	declare @testRunId int = (select top 1 TestRunId from TestRuns order by TestRunID desc);
	insert into TestRunTables(TestRunID,TableID,StartAt,EndAt)
	values (@testRunId,@numar,@start,@intermediate);

	insert into TestRunViews(TestRunID,ViewID,StartAt,EndAt)
	values (@testRunId,@numar,@intermediate,@finish);
end;

exec test 2;
select * from TestRuns;
select * from TestRunTables;
select * from TestRunViews;
delete from CarteAutor;
delete from Autor;
delete from Carte;

select * from Autor;
select * from Carte;
select * from CarteAutor;
delete from TestRuns;