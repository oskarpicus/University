import 'package:expense_management/database/database.dart';
import 'package:expense_management/model/expense_type.dart';
import 'package:expense_management/service/expense_view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class AddExpenseForm extends StatefulWidget {
  const AddExpenseForm({Key? key}) : super(key: key);

  @override
  _AddExpenseFormState createState() => _AddExpenseFormState();
}

class _AddExpenseFormState extends State<AddExpenseForm> {
  final _formKey = GlobalKey<FormState>();
  late String _name;
  late double _sum;
  late ExpenseType _type;
  late DateTime _dueOn;
  late bool _payed;

  late List<DropdownMenuItem<ExpenseType>> _expenseTypes;

  @override
  void initState() {
    _expenseTypes = _getExpenseTypes();
    setState(() {
      _dueOn = DateTime.now();
      _payed = false;
      _type = ExpenseType.values[0];
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    TextEditingController controllerDatePicker = TextEditingController();
    controllerDatePicker.text = _dueOn.toString();

    return SingleChildScrollView(
        child: Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          TextFormField(
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
              value: ExpenseType.values[0],
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
              child: const Text('Submit'),
              onPressed: () {
                if (_formKey.currentState!.validate()) {
                  // everything is valid
                  _saveExpense(context);
                }
              },
            ),
            padding: const EdgeInsets.only(top: 40.0),
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
            initialDate: _dueOn,
            firstDate: DateTime(2015, 8),
            lastDate: DateTime(2101))
        .then((picked) {
      if (picked != null && picked != _dueOn) {
        setState(() {
          _dueOn = picked;
        });
      }
    });
  }

  void _saveExpense(BuildContext context) {
    var viewModel = Provider.of<ExpenseViewModel>(context, listen: false);
    var expense = _createExpense();
    viewModel
        .addExpense(expense)
        .then((value) => Scaffold.of(context)
            .showSnackBar(const SnackBar(content: Text('Saved successfully'))))
        .catchError((error) => Scaffold.of(context)
            .showSnackBar(SnackBar(content: Text(error.toString()))));
  }

  Expense _createExpense() {
    return Expense(
        id: "",
        savedOnlyLocally: false,
        name: _name,
        sum: _sum,
        type: _type,
        date: DateTime.now(),
        dueOn: _dueOn,
        payed: _payed);
  }
}
