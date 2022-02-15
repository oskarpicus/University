package service;

import model.Expense;
import repository.ExpenseRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MasterService implements Service {
    private final ExpenseRepository expenseRepository;

    public MasterService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense addExpense(Expense expense) {
        expense.setId(UUID.randomUUID().toString());
        return expenseRepository.save(expense).isEmpty() ? expense : null;
    }

    @Override
    public Expense deleteExpense(String id) {
        return expenseRepository.delete(id).orElse(null);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        return expenseRepository.update(expense).isEmpty() ? expense : null;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return StreamSupport.stream(expenseRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Expense findExpense(String id) {
        return expenseRepository.find(id).orElse(null);
    }
}
