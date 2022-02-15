// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'expense_rest_client.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ExpenseSerializable _$ExpenseSerializableFromJson(Map<String, dynamic> json) =>
    ExpenseSerializable(
      id: json['id'] as String?,
      name: json['name'] as String?,
      sum: (json['sum'] as num?)?.toDouble(),
      type: $enumDecodeNullable(_$ExpenseTypeEnumMap, json['type']),
      date:
          json['date'] == null ? null : DateTime.parse(json['date'] as String),
      dueOn: json['dueOn'] == null
          ? null
          : DateTime.parse(json['dueOn'] as String),
      payed: json['payed'] as bool?,
    );

Map<String, dynamic> _$ExpenseSerializableToJson(
        ExpenseSerializable instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'sum': instance.sum,
      'type': _$ExpenseTypeEnumMap[instance.type],
      'date': instance.date?.toIso8601String(),
      'dueOn': instance.dueOn?.toIso8601String(),
      'payed': instance.payed,
    };

const _$ExpenseTypeEnumMap = {
  ExpenseType.food: 'food',
  ExpenseType.clothing: 'clothing',
  ExpenseType.phone: 'phone',
  ExpenseType.utilities: 'utilities',
  ExpenseType.others: 'others',
};

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

class _ExpenseRestClient implements ExpenseRestClient {
  _ExpenseRestClient(this._dio, {this.baseUrl}) {
    baseUrl ??= 'http://172.20.10.9:8080';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<List<ExpenseSerializable>> getExpenses() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(
        _setStreamType<List<ExpenseSerializable>>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/expenses',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data!
        .map((dynamic i) =>
            ExpenseSerializable.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  @override
  Future<ExpenseSerializable> findExpense(id) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<ExpenseSerializable>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/expenses/${id}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = ExpenseSerializable.fromJson(_result.data!);
    return value;
  }

  @override
  Future<ExpenseSerializable> saveExpense(expense) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(expense.toJson());
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<ExpenseSerializable>(
            Options(method: 'POST', headers: _headers, extra: _extra)
                .compose(_dio.options, '/expenses',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = ExpenseSerializable.fromJson(_result.data!);
    return value;
  }

  @override
  Future<ExpenseSerializable> deleteExpense(id) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<ExpenseSerializable>(
            Options(method: 'DELETE', headers: _headers, extra: _extra)
                .compose(_dio.options, '/expenses/${id}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = ExpenseSerializable.fromJson(_result.data!);
    return value;
  }

  @override
  Future<ExpenseSerializable> updateExpense(id, expense) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    _data.addAll(expense.toJson());
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<ExpenseSerializable>(
            Options(method: 'PUT', headers: _headers, extra: _extra)
                .compose(_dio.options, '/expenses/${id}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = ExpenseSerializable.fromJson(_result.data!);
    return value;
  }

  RequestOptions _setStreamType<T>(RequestOptions requestOptions) {
    if (T != dynamic &&
        !(requestOptions.responseType == ResponseType.bytes ||
            requestOptions.responseType == ResponseType.stream)) {
      if (T == String) {
        requestOptions.responseType = ResponseType.plain;
      } else {
        requestOptions.responseType = ResponseType.json;
      }
    }
    return requestOptions;
  }
}
