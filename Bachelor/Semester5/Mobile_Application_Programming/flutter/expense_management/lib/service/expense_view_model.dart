import 'dart:developer';

import 'package:expense_management/database/database.dart';
import 'package:expense_management/repository/expense_repository.dart';
import 'package:mobx/mobx.dart';

class ExpenseViewModel {
  final ExpenseRepository _repository;
  bool _synced = false;

  @observable
  late ObservableList<Expense> _expenses;

  ExpenseViewModel(this._repository);

  ObservableList<Expense> getExpenses() {
    _expenses = ObservableList.of([]);
    _repository.findAll().then((expenses) {
      _expenses.addAll(expenses);
    });
    return _expenses;
  }

  Future addExpense(Expense e) {
    return _repository.save(e).then((value) {
      _expenses.add(value);
      if ((value as Expense).savedOnlyLocally) {
        _synced = false;
        throw Exception("Cannot connect to the server. Operation made locally");
      }
    });
  }

  Future deleteExpense(String id) {
    return _repository
        .delete(id)
        .then((value) => _expenses.removeWhere((element) => element.id == id));
  }

  Future updateExpense(Expense e) {
    return _repository.update(e).then((value) {
      int index = _expenses.indexWhere((element) => element.id == e.id);
      _expenses.removeAt(index);
      _expenses.insert(index, e);
    });
  }

  void syncWithServer() {
    if (_synced) {
      return;
    }

    log("Syncing local data with server");
    _synced = true;
    _repository.findExpensesSavedOnlyLocally().then((expenses) {
      for (Expense currentExpense in expenses) {
        addExpense(currentExpense).then((expenseSaved) {
          _repository.deleteLocally(currentExpense).then((value) => _expenses
              .removeWhere((element) => element.id == currentExpense.id));
        }).catchError((error) => _synced = false);
      }
    });
  }
}
