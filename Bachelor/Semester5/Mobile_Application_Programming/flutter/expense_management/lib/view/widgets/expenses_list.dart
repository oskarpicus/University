import 'package:expense_management/database/database.dart';
import 'package:expense_management/service/expense_view_model.dart';
import 'package:expense_management/view/screens/update_delete_expense_screen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:provider/provider.dart';

class ExpensesList extends StatefulWidget {
  const ExpensesList({Key? key}) : super(key: key);

  @override
  _ExpensesListState createState() => _ExpensesListState();
}

class _ExpensesListState extends State<ExpensesList> {
  @override
  Widget build(BuildContext context) {
    var viewModel = Provider.of<ExpenseViewModel>(context);
    var expenses = viewModel.getExpenses();

    return Observer(
        builder: (context) => ListView.builder(
              itemCount: expenses.length * 2,
              padding: const EdgeInsets.all(16.0),
              itemBuilder: (context, i) {
                if (i.isOdd) {
                  return const Divider();
                }
                final index = i ~/ 2;
                return _buildRow(context, expenses[index]);
              },
            ));
  }

  Widget _buildRow(BuildContext context, Expense expense) {
    return ListTile(
      title: Text(
        expense.name,
        style: TextStyle(
            color: Theme.of(context).textSelectionTheme.selectionColor),
      ),
      trailing: Text(
        expense.sum.toString() + ' \$',
        style: TextStyle(
            color: Theme.of(context).textSelectionTheme.selectionColor),
      ),
      tileColor: Theme.of(context).primaryColor,
      onTap: () => _navigateToUpdateDeleteExpenseScreen(context, expense),
    );
  }

  void _navigateToUpdateDeleteExpenseScreen(
      BuildContext context, Expense expense) {
    Navigator.of(context).push(MaterialPageRoute<void>(
        builder: (context) => UpdateDeleteExpenseScreen(expense)));
  }
}
