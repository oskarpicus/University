import 'package:expense_management/model/expense_type.dart';
import 'package:moor_flutter/moor_flutter.dart';

part 'database.g.dart';

class Expenses extends Table {
  TextColumn get id => text()();

  TextColumn get name => text()();

  RealColumn get sum => real()();

  IntColumn get type => intEnum<ExpenseType>().nullable()();

  DateTimeColumn get date => dateTime().withDefault(Constant(DateTime.now()))();

  DateTimeColumn get dueOn => dateTime().nullable()();

  BoolColumn get payed => boolean().withDefault(const Constant(false))();

  BoolColumn get savedOnlyLocally => boolean().withDefault(const Constant(false))();

  @override
  Set<Column> get primaryKey => {id};
}

@UseMoor(tables: [Expenses])
class AppDatabase extends _$AppDatabase {
  AppDatabase()
      : super(FlutterQueryExecutor.inDatabaseFolder(
            path: 'db.sqlite', logStatements: true));

  @override
  int get schemaVersion => 1;

  Future<List<Expense>> getAllExpenses() => select(expenses).get();

  Stream<List<Expense>> watchAllExpenses() => select(expenses).watch();

  Future insertExpense(Expense expense) => into(expenses).insert(expense);

  Future updateExpense(Expense expense) => update(expenses).replace(expense);

  Future deleteExpense(Expense expense) => delete(expenses).delete(expense);

  Future findExpense(String id) => (select(expenses)..where((tbl) => tbl.id.equals(id))).getSingle();

  Future<List<Expense>> getExpensesSavedOnlyLocally() => (select(expenses)..where((tbl) => tbl.savedOnlyLocally)).get();
}
