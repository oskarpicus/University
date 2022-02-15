import 'package:expense_management/view/widgets/expenses_list.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'add_expense_screen.dart';

class DisplayExpensesScreen extends StatelessWidget {
  const DisplayExpensesScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).backgroundColor,
      appBar: AppBar(
        title: const Text('Expenses App'),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: const ExpensesList(),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _navigateToAddExpense(context);
        },
        child: const Icon(
          Icons.plus_one_outlined,
          color: Colors.black,
        ),
        backgroundColor: Colors.white,
      ),
    );
  }

  void _navigateToAddExpense(BuildContext context) {
    Navigator.of(context).push(MaterialPageRoute<void>(builder: (context) {
      return const AddExpenseScreen();
    }));
  }
}
