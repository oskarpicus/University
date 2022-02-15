package service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Expense
import repository.InMemoryExpenseRepository
import repository.Repository
import validator.ExpenseValidator
import java.util.*

class MyViewModel : ViewModel(), Service {
    private var expenseRepository: Repository<String, Expense> = InMemoryExpenseRepository(ExpenseValidator())
    private val expenses: MutableLiveData<List<Expense>> = MutableLiveData<List<Expense>>(findAllExpenses())

    fun getExpenses(): LiveData<List<Expense>> {
        return expenses
    }

    override fun findAllExpenses(): List<Expense> {
        return expenseRepository.findAll()
    }

    override fun addExpense(e: Expense): Optional<Expense> {
        return expenseRepository.save(e)
    }

    override fun deleteExpense(id: String): Optional<Expense> {
        return expenseRepository.delete(id)
    }

    override fun updateExpense(e: Expense): Optional<Expense> {
        return expenseRepository.update(e)
    }

    override fun findExpenseById(id: String): Optional<Expense> {
        return expenseRepository.find(id)
    }

    companion object {
        private lateinit var instance: MyViewModel

        fun getInstance(): MyViewModel {
            instance = if(::instance.isInitialized) instance else MyViewModel()
            return instance
        }
    }
}
