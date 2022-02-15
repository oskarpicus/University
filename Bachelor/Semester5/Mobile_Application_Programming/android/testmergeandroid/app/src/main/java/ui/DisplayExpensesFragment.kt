package ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testmergeandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import service.MyViewModel

class DisplayExpensesFragment : Fragment(R.layout.display_expenses), RecycleViewClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myViewModel = MyViewModel.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(context)
        viewAdapter = MyAdapter(myViewModel.getExpenses().value!!.toTypedArray(), this)
        recyclerView = view.findViewById<RecyclerView?>(R.id.recycler_view).apply {
            setHasFixedSize(false)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        myViewModel.getExpenses().observe(this) { expenses ->
            viewAdapter.updateData(expenses.toTypedArray())
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
            .setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_displayExpensesFragment_to_addExpense)
            }
    }

    override fun recyclerViewListClicked(v: View, expenseId: String) {
        val expense = myViewModel.findExpenseById(expenseId)
        if (expense.isPresent) {
            val bundle = bundleOf("expense" to expense.get())
            Navigation.findNavController(view!!).navigate(R.id.action_displayExpensesFragment_to_updateExpense, bundle)
        }
    }
}