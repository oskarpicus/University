import 'package:dio/dio.dart';
import 'package:expense_management/database/database.dart';
import 'package:expense_management/model/expense_type.dart';
import 'package:json_annotation/json_annotation.dart';
import 'package:retrofit/http.dart';

part 'expense_rest_client.g.dart';

@RestApi(baseUrl: "http://172.20.10.9:8080")
abstract class ExpenseRestClient {
  factory ExpenseRestClient(Dio dio, {String baseUrl}) = _ExpenseRestClient;

  @GET("/expenses")
  Future<List<ExpenseSerializable>> getExpenses();

  @GET("/expenses/{id}")
  Future<ExpenseSerializable> findExpense(@Path("id") String id);

  @POST("/expenses")
  Future<ExpenseSerializable> saveExpense(@Body() ExpenseSerializable expense);

  @DELETE("/expenses/{id}")
  Future<ExpenseSerializable> deleteExpense(@Path("id") String id);

  @PUT("/expenses/{id}")
  Future<ExpenseSerializable> updateExpense(@Path("id") String id, @Body() ExpenseSerializable expense);
}

@JsonSerializable()
class ExpenseSerializable {
  String? id;
  String? name;
  double? sum;
  ExpenseType? type;
  DateTime? date;
  DateTime? dueOn;
  bool? payed;

  ExpenseSerializable(
      {this.id,
      this.name,
      this.sum,
      this.type,
      this.date,
      this.dueOn,
      this.payed});

  factory ExpenseSerializable.fromJson(Map<String, dynamic> json) =>
      _$ExpenseSerializableFromJson(json);

  Map<String, dynamic> toJson() => _$ExpenseSerializableToJson(this);

  Expense to() {
    return Expense(
        id: id!, name: name!, sum: sum!, date: date!, payed: payed!, type: type!, dueOn: dueOn!, savedOnlyLocally: false);
  }

  static ExpenseSerializable from(Expense e) {
    var result = ExpenseSerializable();
    result.id = e.id;
    result.sum = e.sum;
    result.name = e.name;
    result.type = e.type;
    result.date = e.date;
    result.dueOn = e.dueOn;
    result.payed = e.payed;
    return result;
  }
}
