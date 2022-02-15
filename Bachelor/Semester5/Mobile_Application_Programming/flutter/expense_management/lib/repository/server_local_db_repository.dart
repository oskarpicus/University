import 'dart:developer';

import 'package:dio/dio.dart';
import 'package:expense_management/database/database.dart';
import 'package:expense_management/repository/expense_repository.dart';
import 'package:expense_management/retrofit/expense_rest_client.dart';
import 'package:expense_management/validator/validator.dart';
import 'package:uuid/uuid.dart';

class ServerLocalDbRepository implements ExpenseRepository {
  final Validator<Expense> _validator;
  final AppDatabase _db;
  final ExpenseRestClient _restClient;

  ServerLocalDbRepository(this._validator, this._db, this._restClient) {
    log("ana are mere");
  }

  @override
  Future delete(String id) {
    return _restClient
        .deleteExpense(id)
        .then((value) => _db.deleteExpense(value.to()))
        .catchError(_handleError);
  }

  @override
  Future<List<Expense>> findAll() {
    return _restClient
        .getExpenses()
        .then((value) => value.map((e) => e.to()).toList())
        .catchError((error) => _db.getAllExpenses());
  }

  @override
  Future save(Expense e) {
    return _restClient.saveExpense(ExpenseSerializable.from(e)).then((value) {
      Expense expense = value.to();
      _db.insertExpense(expense);
      return expense;
    }).catchError((error) {
      _validator.validate(e);
      Expense expense = Expense(
          id: const Uuid().v4(),
          name: e.name,
          date: e.date,
          dueOn: e.dueOn,
          payed: e.payed,
          type: e.type,
          savedOnlyLocally: true,
          sum: e.sum);
      _db.insertExpense(expense);
      return expense;
    });
  }

  @override
  Future update(Expense e) {
    return _restClient
        .updateExpense(e.id, ExpenseSerializable.from(e))
        .then((value) => _db.updateExpense(value.to()))
        .catchError((Object object) => _handleError(object));
  }

  void _handleError(Object error) {
    var dioError = (error as DioError);
    log(error.toString());
    if (dioError.response == null) {
      throw Exception(
          "Cannot connect to server. Operation not permitted offline");
    }

    throw Exception(dioError.response.toString());
  }

  @override
  Future find(String id) {
    return _restClient.findExpense(id).catchError((error) {
      var dioError = (error as DioError);
      if (dioError.response == null) {
        return _db.findExpense(id);
      }
      throw Exception(dioError.message);
    });
  }

  @override
  Future<List<Expense>> findExpensesSavedOnlyLocally() {
    return _db.getExpensesSavedOnlyLocally();
  }

  @override
  Future deleteLocally(Expense expense) {
    return _db.deleteExpense(expense);
  }
}
