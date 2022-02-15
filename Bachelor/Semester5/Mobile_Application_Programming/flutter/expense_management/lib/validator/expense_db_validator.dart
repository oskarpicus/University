import 'package:expense_management/database/database.dart';
import 'package:expense_management/validator/validation_exception.dart';
import 'package:expense_management/validator/validator.dart';

class ExpenseDbValidator implements Validator<Expense> {
  @override
  void validate(Expense e) {
    String errors = "";
    if (e.name == "") {
      errors += "Invalid name\n";
    }
    if (e.sum <= 0) {
      errors += "Invalid sum\n";
    }
    if (errors != "") {
      throw ValidationException(errors);
    }
  }
}
