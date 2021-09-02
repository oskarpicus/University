use Biblioteca;
go

create or alter procedure uspPhantomReads2
as begin
	begin tran
		insert into Tema(denumire, curent)
		values ('ANA', 'MERE');

		insert into LogTable(nameOperation, result)
		values ('uspPhantomReads2', cast(@@ROWCOUNT as varchar) + ' rows affected');

	commit tran
end;

exec uspPhantomReads2;