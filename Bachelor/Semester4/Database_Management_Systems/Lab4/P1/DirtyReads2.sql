use Biblioteca;
go

create or alter procedure uspDirtyReads2
as begin
	set transaction isolation level read uncommitted;
	-- solution
	-- set transaction isolation level read committed;
	begin tran
		select * from Tema;

		declare @lastName varchar(10);
		set @lastName = (select top 1 denumire from Tema order by tid desc);

		insert into LogTable(nameOperation, result)
		values ('uspDirtyReads2', 'Last name ' + @lastName);

		commit tran;
end;

exec uspDirtyReads2;