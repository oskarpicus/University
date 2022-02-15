import 'dart:developer';

import 'package:expense_management/retrofit/my_connectivity.dart';
import 'package:expense_management/repository/server_local_db_repository.dart';
import 'package:expense_management/retrofit/expense_rest_client.dart';
import 'package:expense_management/service/expense_view_model.dart';
import 'package:expense_management/validator/expense_db_validator.dart';
import 'package:expense_management/view/screens/display_expenses_screen.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:dio/dio.dart';
import 'package:connectivity_plus/connectivity_plus.dart';

import 'database/database.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var expenseViewModel = ExpenseViewModel(ServerLocalDbRepository(
        ExpenseDbValidator(), AppDatabase(), ExpenseRestClient(Dio())));
    MyConnectivity.instance.initialise();
    MyConnectivity.instance.myStream.listen((event) {
      log(event.toString());
      // if the device is online
      if ((event as Map).keys.toList()[0] != ConnectivityResult.none) {
        expenseViewModel.syncWithServer();
      }
    });

    return Provider(
        create: (_) => expenseViewModel,
        child: MaterialApp(
          title: 'Expenses Management',
          home: const DisplayExpensesScreen(),
          theme: ThemeData(
            backgroundColor: Colors.blue[50],
            primaryColor: Colors.indigo[900],
            fontFamily: 'Verdana',
            textSelectionTheme:
                const TextSelectionThemeData(selectionColor: Colors.white),
          ),
        ));
  }
}
