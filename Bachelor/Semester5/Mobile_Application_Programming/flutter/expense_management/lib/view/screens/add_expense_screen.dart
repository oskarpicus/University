import 'package:expense_management/view/widgets/add_expense_form.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class AddExpenseScreen extends StatelessWidget {
  const AddExpenseScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Add an expense"),
          backgroundColor: Theme.of(context).primaryColor,
        ),
        body: Container(
          margin:
              const EdgeInsets.only(top: 10, left: 10, right: 10, bottom: 10),
          child: const AddExpenseForm(),
        ),
        backgroundColor: Theme.of(context).backgroundColor
    );
  }
}
