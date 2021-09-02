use Biblioteca;
go

create or alter procedure uspDeadlock1
as begin
	set deadlock_priority low;
	begin tran
		update Tema
		set denumire = denumire + 'B'
		where tid = 1;

		insert into LogTable(nameOperation, result)
		values ('uspDeadlock1', 'Updated Tema B ' + cast(@@ROWCOUNT as varchar));

		waitfor delay '00:00:10';

		update Carte
		set titlu = titlu + 'C'
		where titlu like 'Zeii%';

		insert into LogTable(nameOperation, result)
		values ('uspDeadlock1', 'Updated Carte C ' + cast(@@ROWCOUNT as varchar));
	commit tran
end;

exec uspDeadlock1;

select * from LogTable;
select * from Tema;
select * from Carte;
delete from LogTable where id>=56;

update Tema
set denumire='iubire', curent='romantism'
where tid=1;

update Carte
set titlu='Zeii'
where titlu like 'Zeii%';


-- solution
go
create or alter procedure uspDeadlockSolution1
as begin
	set deadlock_priority low;
	begin tran
		update Carte
		set titlu = titlu + 'C'
		where titlu like 'Zeii%';

		insert into LogTable(nameOperation, result)
		values ('uspDeadlockSolution1', 'Updated Carte C ' + cast(@@ROWCOUNT as varchar));

		waitfor delay '00:00:10';

		update Tema
		set denumire = denumire + 'B'
		where tid = 1;

		insert into LogTable(nameOperation, result)
		values ('uspDeadlockSolution1', 'Updated Tema B ' + cast(@@ROWCOUNT as varchar));
	commit tran
end;

exec uspDeadlockSolution1;