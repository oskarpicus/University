use Biblioteca;
go

create or alter procedure uspDirtyReads1
as begin
	begin tran
		insert into Tema(denumire, curent)
		values ('autobiografie', 'modernism');

		waitfor delay '00:00:5';

		insert into LogTable(nameOperation, result)
		values ('uspDirtyReads1', 'Will rollback ' + cast(@@ROWCOUNT as varchar));

		rollback tran;
end;

exec uspDirtyReads1;
select * from Tema;

select * from LogTable;