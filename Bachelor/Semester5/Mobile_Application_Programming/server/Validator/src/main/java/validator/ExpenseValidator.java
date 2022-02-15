package validator;

import model.Expense;

public class ExpenseValidator {
    public void validate(Expense expense) throws ValidationException {
        String errors = "";
        if (expense.getName().equals("")) {
            errors += "Invalid name\n";
        }
        if (expense.getSum() <= 0) {
            errors += "Invalid sum\n";
        }
        if (!errors.equals("")) {
            throw new ValidationException(errors);
        }
    }
}
