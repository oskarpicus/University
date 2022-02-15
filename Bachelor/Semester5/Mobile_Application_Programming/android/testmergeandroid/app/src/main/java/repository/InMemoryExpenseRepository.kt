package repository

import model.Expense
import model.ExpenseType
import validator.Validator
import java.time.LocalDate
import java.util.*

class InMemoryExpenseRepository(override val validator: Validator<Expense>) : Repository<String, Expense> {
    private val today: LocalDate = LocalDate.now()
    private val tomorrow: LocalDate = today.plusDays(1)
    private var expenses = mutableListOf(
        Expense("1", "Electricity", 100f, ExpenseType.UTILITIES, today, tomorrow, false),
        Expense("2", "Heating", 320f, ExpenseType.UTILITIES, today, tomorrow, true),
        Expense("3", "Lunch", 15f, ExpenseType.FOOD, today, tomorrow, true),
        Expense("4", "Phone bill", 83f, ExpenseType.PHONE, today, tomorrow, true),
        Expense("5", "H&M T-shirt", 12f, ExpenseType.CLOTHING, today, tomorrow, true),
        Expense("6", "Rent", 222f, ExpenseType.OTHERS, today, tomorrow, true),
    )

    override fun save(e: Expense): Optional<Expense> {
        validator.validate(e)
        val result = expenses.add(e)
        e.id = UUID.randomUUID().toString()
        if (result) {
            return Optional.empty()
        }
        return Optional.empty()
    }

    override fun delete(id: String): Optional<Expense> {
        val index = getIndex(id)
        if (index == -1) {
            return Optional.empty()
        }
        return Optional.of(expenses.removeAt(index))
    }

    override fun update(e: Expense): Optional<Expense> {
        validator.validate(e)
        val index = getIndex(e.id)
        if (index != -1) {
            expenses[index] = e
            return Optional.empty()
        }
        return Optional.of(e)
    }

    override fun find(id: String): Optional<Expense> {
        return expenses.stream()
            .filter { e -> e.id == id }
            .findFirst()
    }

    override fun findAll(): List<Expense> {
        return expenses
    }

    private fun getIndex(id: String) : Int {
        var result = -1
        expenses.forEachIndexed { index, expense ->
            if (id == expense.id) {
                result = index
            }
        }
        return result
    }
}