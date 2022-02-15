import 'package:expense_management/database/database.dart';
import 'package:expense_management/repository/repository.dart';

abstract class ExpenseRepository extends Repository<String, Expense> {
  Future<List<Expense>> findExpensesSavedOnlyLocally();

  Future deleteLocally(Expense expense);
}