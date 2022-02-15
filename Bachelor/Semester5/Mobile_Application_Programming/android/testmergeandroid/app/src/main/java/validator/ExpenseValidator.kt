package validator

import model.Expense

class ExpenseValidator : Validator<Expense> {
    override fun validate(e: Expense) {
        var errors = ""
        if (e.name == "")
            errors += "Invalid name\n"
        if (e.sum <= 0)
            errors += "Invalid sum\n"
        if(errors != "")
            throw ValidationException(errors)
    }
}