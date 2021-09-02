use Biblioteca;
go

create or alter procedure uspPhantomReads1
as begin
	declare @lastId int;
	-- solution
	-- set transaction isolation level Serializable
	begin tran
		select * from Tema;
		set @lastId = (select top 1 tid from Tema order by tid desc);

		insert into LogTable(nameOperation, result)
		values ('uspPhantomReads1', 'Last id: ' + cast(@lastId as varchar));

		waitfor delay '00:00:05';

		select * from Tema;
		set @lastId = (select top 1 tid from Tema order by tid desc);

		insert into LogTable(nameOperation, result)
		values ('uspPhantomReads1', 'Last id: ' + cast(@lastId as varchar));
	commit tran
end;

exec uspPhantomReads1;
select * from Tema;
select * from LogTable;

delete from Tema where denumire='ANA';