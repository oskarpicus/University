import 'expense_type.dart';

class Expense {
  String? id;
  String? name;
  double? sum;
  ExpenseType? type;
  DateTime? date;
  DateTime? dueOn;
  bool? payed;

  Expense(this.id, this.name, this.sum, this.type, this.date, this.dueOn, this.payed);

  Expense.empty();
}
