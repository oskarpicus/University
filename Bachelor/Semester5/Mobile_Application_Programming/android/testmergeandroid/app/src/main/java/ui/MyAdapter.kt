package ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testmergeandroid.R
import model.Expense

class MyAdapter(private var dataSet: Array<Expense>, private var listener: RecycleViewClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val textViewExpenseName: TextView
        val textViewExpenseSum: TextView
        val textViewExpenseId: TextView

        init {
            textViewExpenseName = view.findViewById(R.id.text_view_expense_name)
            textViewExpenseSum = view.findViewById(R.id.text_view_expense_sum)
            textViewExpenseId = view.findViewById(R.id.text_view_expense_id)

            textViewExpenseId.setOnClickListener(this)
            textViewExpenseSum.setOnClickListener(this)
            textViewExpenseName.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.recyclerViewListClicked(p0!!, textViewExpenseId.text as String)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewExpenseName.text = dataSet[position].name
        holder.textViewExpenseSum.text = dataSet[position].sum.toString() + " $"
        holder.textViewExpenseId.text = dataSet[position].id
    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(data: Array<Expense>) {
        dataSet = data
        notifyDataSetChanged()
    }
}