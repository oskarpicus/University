use Biblioteca;
go

create or alter procedure uspUnrepeatableReads2
as begin

	begin tran
		update Tema
		set curent = curent + 'A'
		where tid = 1;

		insert into LogTable(nameOperation, result)
		values ('uspUnrepeatableReads2', cast(@@ROWCOUNT as varchar) + ' rows affected');
	commit tran

end;

exec uspUnrepeatableReads2;