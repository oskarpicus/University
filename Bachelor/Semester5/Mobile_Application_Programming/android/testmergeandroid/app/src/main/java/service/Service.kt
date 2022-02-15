package service

import model.Expense
import repository.Repository
import java.io.Serializable
import java.util.*

interface Service : Serializable {
    fun findAllExpenses(): List<Expense>

    fun addExpense(e: Expense): Optional<Expense>

    fun deleteExpense(id: String): Optional<Expense>

    fun updateExpense(e: Expense): Optional<Expense>

    fun findExpenseById(id: String): Optional<Expense>
}