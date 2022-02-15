import 'package:expense_management/database/database.dart';
import 'package:expense_management/model/expense_type.dart';
import 'package:expense_management/service/expense_view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class UpdateDeleteExpenseForm extends StatefulWidget {
  final Expense _expense;

  const UpdateDeleteExpenseForm(this._expense, {Key? key}) : super(key: key);

  @override
  _UpdateDeleteExpenseFormState createState() =>
      _UpdateDeleteExpenseFormState();
}

class _UpdateDeleteExpenseFormState extends State<UpdateDeleteExpenseForm> {
  final _formKey = GlobalKey<FormState>();
  late List<DropdownMenuItem<ExpenseType>> _expenseTypes;
  late String _name;
  late double _sum;
  late ExpenseType _type;
  late DateTime _dueOn;
  late bool _payed;

  @override
  void initState() {
    _expenseTypes = _getExpenseTypes();
    setState(() {
      _name = widget._expense.name;
      _sum = widget._expense.sum;
      _type = widget._expense.type!;
      _dueOn = widget._expense.dueOn!;
      _payed = widget._expense.payed;
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    TextEditingController controllerDatePicker = TextEditingController();
    controllerDatePicker.text = widget._expense.dueOn!.toString();

    return SingleChildScrollView(
        child: Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          TextFormField(
            initialValue: widget._expense.name,
            onChanged: (val) => setState(() => _name = val),
            decoration: const InputDecoration(
              labelText: 'Name',
              hintText: 'Enter the name of the expense',
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter a valid name';
              }
              return null;
            },
          ),
          TextFormField(
              initialValue: widget._expense.sum.toString(),
              //controller: controllerTextFormSum,
              keyboardType: TextInputType.number,
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please enter a valid number';
                }
                return null;
              },
              onChanged: (val) =>
                  setState(() => _sum = double.parse(val == "" ? "0" : val)),
              decoration: const InputDecoration(
                  labelText: 'Sum', hintText: 'Enter the sum of the expense')),
          DropdownButtonFormField(
              items: _expenseTypes,
              value: widget._expense.type,
              onChanged: (type) => setState(() => {
                    _type = ExpenseType.values
                        .firstWhere((e) => e.toString() == type.toString())
                  }),
              validator: (value) {
                if (value == null) {
                  return 'Please select an expense type';
                }
                return null;
              }),
          TextFormField(
              controller: controllerDatePicker,
              decoration: const InputDecoration(
                labelText: 'Due on',
                hintText: 'The date until you can pay the expense',
              ),
              onTap: () => _selectDate(context, controllerDatePicker),
              validator: (value) {
                if (value == null || value.isEmpty) {
                  return 'Please select a date';
                }
                return null;
              }),
          CheckboxListTile(
            title: const Text('Payed'),
            value: _payed,
            onChanged: (newValue) => setState(() => _payed = newValue!),
          ),
          Container(
            // the submit button
            child: ElevatedButton(
              child: const Text('Update'),
              onPressed: () {
                if (_formKey.currentState!.validate()) {
                  // everything is valid
                  _updateExpense(context);
                }
              },
            ),
            padding: const EdgeInsets.only(top: 40.0),
          ),
          Container(
            child: ElevatedButton(
              child: const Text('Delete'),
              onPressed: () => _showAlertDialogForDelete(context),
            ),
            margin: const EdgeInsets.only(top: 40),
          )
        ],
      ),
    ));
  }

  List<DropdownMenuItem<ExpenseType>> _getExpenseTypes() {
    return ExpenseType.values
        .map((e) =>
            DropdownMenuItem(value: e, child: Text(e.toString().split(".")[1])))
        .toList();
  }

  void _selectDate(BuildContext context, TextEditingController controller) {
    showDatePicker(
            context: context,
            initialDate: widget._expense.dueOn!,
            firstDate: DateTime(2015, 8),
            lastDate: DateTime(2101))
        .then((picked) {
      if (picked != null && picked != widget._expense.dueOn) {
        setState(() {
          _dueOn = picked;
        });
      }
    });
  }

  void _updateExpense(BuildContext context) {
    Expense expense = _createExpense();
    Provider.of<ExpenseViewModel>(context, listen: false)
        .updateExpense(expense)
        .then((value) => Scaffold.of(context).showSnackBar(
            const SnackBar(content: Text('Updated successfully'))))
        .catchError((error) => Scaffold.of(context)
            .showSnackBar(SnackBar(content: Text(error.toString()))));
  }

  void _deleteExpense(BuildContext context) {
    var viewModel = Provider.of<ExpenseViewModel>(context, listen: false);
    viewModel.deleteExpense(widget._expense.id).then((value) {
      Scaffold.of(context)
          .showSnackBar(const SnackBar(content: Text('Deleted successfully')));
      Navigator.of(context).pop(); // remove the screen
    }).catchError((error) {
      Scaffold.of(context)
          .showSnackBar(SnackBar(content: Text(error.toString())));
    });
    Navigator.of(context).pop(); // remove the dialog
  }

  void _showAlertDialogForDelete(BuildContext contextParam) {
    Widget cancelButton = TextButton(
      child: const Text('No'),
      onPressed: () => Navigator.of(contextParam).pop(),
    );

    Widget confirmButton = TextButton(
        child: const Text('Yes'),
        onPressed: () => _deleteExpense(contextParam));

    AlertDialog alertDialog = AlertDialog(
        title: const Text('Remove expense'),
        content: Text('Are you sure that you want to remove the ' +
            widget._expense.name +
            ' expense?'),
        actions: [cancelButton, confirmButton]);

    showDialog(context: contextParam, builder: (context) => alertDialog);
  }

  Expense _createExpense() {
    return Expense(
        id: widget._expense.id,
        savedOnlyLocally: false,
        name: _name,
        sum: _sum,
        type: _type,
        date: widget._expense.date,
        dueOn: _dueOn,
        payed: _payed);
  }
}
