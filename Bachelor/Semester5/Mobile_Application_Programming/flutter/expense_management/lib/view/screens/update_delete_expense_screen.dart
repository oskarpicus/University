import 'package:expense_management/database/database.dart';
import 'package:expense_management/view/widgets/update_delete_expense_form.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class UpdateDeleteExpenseScreen extends StatelessWidget {
  final Expense _expense;

  const UpdateDeleteExpenseScreen(this._expense, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Update Expense'),
          backgroundColor: Theme.of(context).primaryColor,
        ),
        body: Container(
          margin:
              const EdgeInsets.only(top: 10, left: 10, right: 10, bottom: 10),
          child: UpdateDeleteExpenseForm(_expense),
        ));
  }
}
