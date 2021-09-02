use Biblioteca;
go

create or alter procedure uspUnrepeatableReads1
as begin
	declare @curent varchar(20);

	-- solution
	-- set transaction isolation level repeatable read;

	begin tran
		select * from Tema;

		set @curent = (select curent from Tema where tid = 1);
		insert into LogTable(nameOperation, result)
		values ('uspUnrepeatableReads1', 'Curent ' + @curent);

		waitfor delay '00:00:05';
		select * from Tema;

		set @curent = (select curent from Tema where tid = 1);
		insert into LogTable(nameOperation, result)
		values ('uspUnrepeatableReads1', 'Curent ' + @curent);
	commit tran
end;

exec uspUnrepeatableReads1;
select * from Tema;
select * from LogTable;