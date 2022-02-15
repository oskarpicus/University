package service;

import model.Expense;

import java.util.List;

public interface Service {
    Expense addExpense(Expense expense);

    Expense deleteExpense(String id);

    Expense updateExpense(Expense expense);

    List<Expense> getAllExpenses();

    Expense findExpense(String id);
}
