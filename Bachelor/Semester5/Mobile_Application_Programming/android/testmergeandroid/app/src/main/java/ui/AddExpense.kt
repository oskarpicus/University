package ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.testmergeandroid.R
import com.google.android.material.snackbar.Snackbar
import model.Expense
import model.ExpenseType
import service.MyViewModel
import validator.ValidationException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddExpense : Fragment() {
    private lateinit var service: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        service = MyViewModel.getInstance()
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.spinnerSaveExpenseType)
        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ExpenseType.values())

        view.findViewById<Button>(R.id.button_save_expense)
            .setOnClickListener {
                val expense = getExpense()
                if (!expense.isPresent) {
                    displaySnackbar("You need to complete all fields")
                } else {
                    try {
                        val result = service.addExpense(expense.get())
                        if (result.isPresent) {  // unsuccessful
                            displaySnackbar("Failed to save. Maybe try again ?")
                        } else {
                            displaySnackbar("Saved successfully")
                        }
                    } catch (e: ValidationException) {
                        displaySnackbar(e.message!!)
                    }
                }
            }
    }

    private fun getExpense(): Optional<Expense> {
        val name = view!!.findViewById<EditText>(R.id.editTextTextSaveExpenseName).text.toString()

        val sum: Float
        try {
            sum = view!!.findViewById<EditText>(R.id.editTextTextSaveExpenseSum).text.toString().toFloat()
        } catch (e: NumberFormatException) {
            return Optional.empty()
        }

        val type =
            ExpenseType.valueOf(view!!.findViewById<Spinner>(R.id.spinnerSaveExpenseType).selectedItem.toString())

        val dueOn: LocalDate
        try {
            val pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu", Locale.ENGLISH)
            dueOn = LocalDate.parse(view!!.findViewById<EditText>(R.id.editTextTextSaveExpenseDate).text, pattern)
        } catch (e: Exception) {
            return Optional.empty()
        }

        val isPayed = view!!.findViewById<CheckBox>(R.id.checkBoxSaveExpensePayed).isChecked

        return Optional.of(Expense("", name, sum, type, LocalDate.now(), dueOn, isPayed))
    }

    private fun displaySnackbar(msg: String) {
        Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

}