package ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
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
import service.Service
import validator.ValidationException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UpdateDeleteExpense : Fragment() {
    private lateinit var service: Service
    private lateinit var editTextName: EditText
    private lateinit var editTextSum: EditText
    private lateinit var editTextDate: EditText
    private lateinit var spinnerType: Spinner
    private lateinit var checkBoxPayed: CheckBox
    private lateinit var buttonUpdate: Button
    private lateinit var buttonDelete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        service = MyViewModel.getInstance()
        return inflater.inflate(R.layout.fragment_update_delete_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextName = view.findViewById(R.id.editTextTextUpdateExpenseName)
        editTextSum = view.findViewById(R.id.editTextTextUpdateExpenseSum)
        editTextDate = view.findViewById(R.id.editTextTextUpdateExpenseDate)
        spinnerType = view.findViewById(R.id.spinnerUpdateExpenseType)
        checkBoxPayed = view.findViewById(R.id.checkBoxUpdateExpensePayed)

        spinnerType.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, ExpenseType.values())

        val expense = arguments?.getSerializable("expense") as Expense
        populateFields(expense)

        buttonUpdate = view.findViewById(R.id.button_update_expense)
        buttonDelete = view.findViewById(R.id.button_delete_expense)

        buttonUpdate.setOnClickListener {
            val updatedExpense = getExpense()
            if (!updatedExpense.isPresent) {
                displaySnackbar("You need to complete all fields")
            }
            else {
                try {
                    updatedExpense.get().id = expense.id
                    val result = service.updateExpense(updatedExpense.get())
                    if (result.isPresent) {
                        displaySnackbar("Failed to update. Maybe try again ?")
                    } else {
                        displaySnackbar("Updated successfully")
                    }
                } catch (e: ValidationException) {
                    displaySnackbar(e.message!!)
                }
            }
        }

        buttonDelete.setOnClickListener {
            DeleteExpenseDialog(expense, view).show(parentFragmentManager, "")
        }
    }

    private fun populateFields(e: Expense) {
        editTextName.text = SpannableStringBuilder(e.name)
        editTextSum.text = SpannableStringBuilder(e.sum.toString())

        val pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu", Locale.ENGLISH)
        editTextDate.text = SpannableStringBuilder(e.dueOn.format(pattern))

        spinnerType.setSelection(e.type.ordinal)
        checkBoxPayed.isChecked = e.payed
    }

    private fun getExpense(): Optional<Expense> {
        val name = editTextName.text.toString()

        val sum: Float
        try {
            sum = editTextSum.text.toString().toFloat()
        } catch (e: NumberFormatException) {
            return Optional.empty()
        }

        val type =
            ExpenseType.valueOf(spinnerType.selectedItem.toString())

        val dueOn: LocalDate
        try {
            val pattern = DateTimeFormatter.ofPattern("dd-MM-uuuu", Locale.ENGLISH)
            dueOn = LocalDate.parse(editTextDate.text, pattern)
        } catch (e: Exception) {
            return Optional.empty()
        }

        val isPayed = checkBoxPayed.isChecked

        return Optional.of(Expense("", name, sum, type, LocalDate.now(), dueOn, isPayed))
    }

    private fun displaySnackbar(msg: String) {
        Snackbar.make(view!!, msg, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }
}
