package model

import java.io.Serializable
import java.time.LocalDate

class Expense(
    var id: String,
    val name: String,
    val sum: Float,
    val type: ExpenseType,
    val addedOn: LocalDate,
    val dueOn: LocalDate,
    val payed: Boolean

) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Expense

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Expense(id='$id', name='$name', sum=$sum, type=$type)"
    }
}
