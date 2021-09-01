use Biblioteca
go

create table Versiune
(
	versiune int primary key
);

insert into Versiune(versiune) values(0);

go
create or alter procedure usp_do_1
as
begin
	-- Modificam tipul coloanei an din Carte in smallint
	alter table Carte
	alter column an smallint;
	print 'an din Carte este acum de tipul smallint';
end

go
create or alter procedure usp_undo_1
as
begin
	-- Modificam tipul coloanei an din Carte inapoi in int
	alter table Carte
	alter column an int;
	print 'an din Carte este acum de tipul int';
end

go
create or alter procedure usp_do_2
as
begin
	-- Adaugam in campul oras din tabela Editura constrangere de valoare implicita
	alter table Editura
	add constraint df_oras default 'Bucuresti' for oras;
	print 'S-a adaugat valorea implicita Bucuresti pentru oras de Editura';
end

go
create or alter procedure usp_undo_2
as
begin
	-- Stergem constrangerea default din orasul editurilor
	alter table Editura
	drop constraint df_oras;
	print 'S-a sters constrangerea default din oras de Editura';
end

go
create or alter procedure usp_do_3
as
begin
	-- Adaugam tabela 
	create table Webpage
	(
		wid int primary key identity,
		nrAccesari int default 0,
		adresa varchar(30),
		protocolSigur bit
	);
	print 'S-a create tabela Webpage' ;
end

go
create or alter procedure usp_undo_3
as
begin
	-- Stergem tabela Webpage
	drop table Webpage;
	print 'S-a sters tabela Webpage';
end

go
create or alter procedure usp_do_4
as begin
	-- Adaugam in tabela Carti campul nrPagini
	alter table Carte
	add nrPagini int;
	print 'S-a creat campul nrPagini in tabela Carti' ;
end

go
create or alter procedure usp_undo_4
as begin
	-- Stergem din tabela Carti campul nrPagini
	alter table Carte
	drop column nrPagini;
	print 'S-a sters campul nrPagini din tabela Carti' ;
end

go
create or alter procedure usp_do_5
as begin
	-- Adaugam cheie straina intre tabelele Webpage si Carte (relatie 1-1, fiecare carte are un webpage)
	alter table Webpage
	add constraint fk_wid foreign key(wid) references Carte(cid);
	print 'Tabelele Carte si Webpage sunt in relatie';
end

go
create or alter procedure usp_undo_5
as begin
	-- Stergem relatia dintre Carte si Webpage
	alter table Webpage
	drop constraint fk_wid;
	print 'Tabelele Carte si Webpage nu mai sunt in relatie';
end


go
create or alter procedure usp_main
( @versiune int )
as 
begin
	set nocount on;
	if(@versiune<0 OR @versiune>5)
	begin
		raiserror('Numar de versiune invalid',10,1);
		return 1;
	end

	declare @versiune_curenta int;
	set @versiune_curenta = (select versiune from Versiune);

	if(@versiune=@versiune_curenta)
	begin
		declare @mesaj varchar(50);
		set @mesaj='Sunteti deja la versiunea '+convert(varchar,@versiune);
		raiserror(@mesaj,10,1);
		return 1;
	end

	declare @nume_proc varchar(50);

	if(@versiune > @versiune_curenta) --apelam procedurile directe
	begin
		set @versiune_curenta=@versiune_curenta+1;
		while(@versiune_curenta <= @versiune)
		begin
			set @nume_proc='usp_do_'+convert(varchar,@versiune_curenta);
			exec @nume_proc;
			set @versiune_curenta=@versiune_curenta+1;
		end
	end

	else -- apelam procedurile inverse
	begin
		while(@versiune_curenta > @versiune)
		begin
			set @nume_proc='usp_undo_'+convert(varchar,@versiune_curenta);
			exec @nume_proc;
			set @versiune_curenta=@versiune_curenta-1;
		end
	end

	--actualizam tabelul de versiuni
	update Versiune
	set versiune=@versiune;
end

exec usp_main 14;
