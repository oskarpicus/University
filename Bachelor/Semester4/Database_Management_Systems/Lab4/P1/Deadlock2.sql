use Biblioteca;
go

create or alter procedure uspDeadlock2
as begin
	set deadlock_priority high;
	begin tran
		update Carte
		set titlu = titlu + 'D'
		where titlu like 'Zeii%';

		insert into LogTable(nameOperation, result)
		values ('uspDeadlock2', 'Updated Carte D ' + cast(@@ROWCOUNT as varchar));

		waitfor delay '00:00:10';

		update Tema
		set denumire = denumire + 'E'
		where tid = 1;

		insert into LogTable(nameOperation, result)
		values ('uspDeadlock2', 'Updated Tema E ' + cast(@@ROWCOUNT as varchar));
	commit tran
end;

exec uspDeadlock2;