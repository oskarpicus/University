package ui

import android.view.View

interface RecycleViewClickListener {
    fun recyclerViewListClicked(v: View, expenseId: String)
}