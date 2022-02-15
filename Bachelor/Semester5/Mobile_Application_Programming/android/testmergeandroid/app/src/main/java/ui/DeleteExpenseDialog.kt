package ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import com.example.testmergeandroid.R
import com.google.android.material.snackbar.Snackbar
import model.Expense
import service.MyViewModel
import service.Service

class DeleteExpenseDialog(private val expense: Expense, private val parentView: View) : DialogFragment() {
    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = MyViewModel.getInstance()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure that you want to delete ${expense.name} expense?")
                .setPositiveButton("Yes"
                ) { _, _ ->
                    // FIRE ZE MISSILES!
                    val result = service.deleteExpense(expense.id)
                    if (result.isPresent) {  // successfully saved
                        Navigation.findNavController(parentView).navigate(R.id.action_updateExpense_to_displayExpensesFragment)
                    } else {
                        Snackbar.make(parentView, "Failed to delete. Try again later", Snackbar.LENGTH_LONG)
                            .setAction("Action", null)
                            .show()
                    }
                }
                .setNegativeButton("No"
                ) { _, _ ->
                    // User cancelled the dialog - no action needed
                }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}