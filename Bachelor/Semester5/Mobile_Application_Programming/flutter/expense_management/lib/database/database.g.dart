// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'database.dart';

// **************************************************************************
// MoorGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps, unnecessary_this
class Expense extends DataClass implements Insertable<Expense> {
  final String id;
  final String name;
  final double sum;
  final ExpenseType? type;
  final DateTime date;
  final DateTime? dueOn;
  final bool payed;
  final bool savedOnlyLocally;
  Expense(
      {required this.id,
      required this.name,
      required this.sum,
      this.type,
      required this.date,
      this.dueOn,
      required this.payed,
      required this.savedOnlyLocally});
  factory Expense.fromData(Map<String, dynamic> data, GeneratedDatabase db,
      {String? prefix}) {
    final effectivePrefix = prefix ?? '';
    return Expense(
      id: const StringType()
          .mapFromDatabaseResponse(data['${effectivePrefix}id'])!,
      name: const StringType()
          .mapFromDatabaseResponse(data['${effectivePrefix}name'])!,
      sum: const RealType()
          .mapFromDatabaseResponse(data['${effectivePrefix}sum'])!,
      type: $ExpensesTable.$converter0.mapToDart(const IntType()
          .mapFromDatabaseResponse(data['${effectivePrefix}type'])),
      date: const DateTimeType()
          .mapFromDatabaseResponse(data['${effectivePrefix}date'])!,
      dueOn: const DateTimeType()
          .mapFromDatabaseResponse(data['${effectivePrefix}due_on']),
      payed: const BoolType()
          .mapFromDatabaseResponse(data['${effectivePrefix}payed'])!,
      savedOnlyLocally: const BoolType().mapFromDatabaseResponse(
          data['${effectivePrefix}saved_only_locally'])!,
    );
  }
  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    map['id'] = Variable<String>(id);
    map['name'] = Variable<String>(name);
    map['sum'] = Variable<double>(sum);
    if (!nullToAbsent || type != null) {
      final converter = $ExpensesTable.$converter0;
      map['type'] = Variable<int?>(converter.mapToSql(type));
    }
    map['date'] = Variable<DateTime>(date);
    if (!nullToAbsent || dueOn != null) {
      map['due_on'] = Variable<DateTime?>(dueOn);
    }
    map['payed'] = Variable<bool>(payed);
    map['saved_only_locally'] = Variable<bool>(savedOnlyLocally);
    return map;
  }

  ExpensesCompanion toCompanion(bool nullToAbsent) {
    return ExpensesCompanion(
      id: Value(id),
      name: Value(name),
      sum: Value(sum),
      type: type == null && nullToAbsent ? const Value.absent() : Value(type),
      date: Value(date),
      dueOn:
          dueOn == null && nullToAbsent ? const Value.absent() : Value(dueOn),
      payed: Value(payed),
      savedOnlyLocally: Value(savedOnlyLocally),
    );
  }

  factory Expense.fromJson(Map<String, dynamic> json,
      {ValueSerializer? serializer}) {
    serializer ??= moorRuntimeOptions.defaultSerializer;
    return Expense(
      id: serializer.fromJson<String>(json['id']),
      name: serializer.fromJson<String>(json['name']),
      sum: serializer.fromJson<double>(json['sum']),
      type: serializer.fromJson<ExpenseType?>(json['type']),
      date: serializer.fromJson<DateTime>(json['date']),
      dueOn: serializer.fromJson<DateTime?>(json['dueOn']),
      payed: serializer.fromJson<bool>(json['payed']),
      savedOnlyLocally: serializer.fromJson<bool>(json['savedOnlyLocally']),
    );
  }
  @override
  Map<String, dynamic> toJson({ValueSerializer? serializer}) {
    serializer ??= moorRuntimeOptions.defaultSerializer;
    return <String, dynamic>{
      'id': serializer.toJson<String>(id),
      'name': serializer.toJson<String>(name),
      'sum': serializer.toJson<double>(sum),
      'type': serializer.toJson<ExpenseType?>(type),
      'date': serializer.toJson<DateTime>(date),
      'dueOn': serializer.toJson<DateTime?>(dueOn),
      'payed': serializer.toJson<bool>(payed),
      'savedOnlyLocally': serializer.toJson<bool>(savedOnlyLocally),
    };
  }

  Expense copyWith(
          {String? id,
          String? name,
          double? sum,
          ExpenseType? type,
          DateTime? date,
          DateTime? dueOn,
          bool? payed,
          bool? savedOnlyLocally}) =>
      Expense(
        id: id ?? this.id,
        name: name ?? this.name,
        sum: sum ?? this.sum,
        type: type ?? this.type,
        date: date ?? this.date,
        dueOn: dueOn ?? this.dueOn,
        payed: payed ?? this.payed,
        savedOnlyLocally: savedOnlyLocally ?? this.savedOnlyLocally,
      );
  @override
  String toString() {
    return (StringBuffer('Expense(')
          ..write('id: $id, ')
          ..write('name: $name, ')
          ..write('sum: $sum, ')
          ..write('type: $type, ')
          ..write('date: $date, ')
          ..write('dueOn: $dueOn, ')
          ..write('payed: $payed, ')
          ..write('savedOnlyLocally: $savedOnlyLocally')
          ..write(')'))
        .toString();
  }

  @override
  int get hashCode =>
      Object.hash(id, name, sum, type, date, dueOn, payed, savedOnlyLocally);
  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      (other is Expense &&
          other.id == this.id &&
          other.name == this.name &&
          other.sum == this.sum &&
          other.type == this.type &&
          other.date == this.date &&
          other.dueOn == this.dueOn &&
          other.payed == this.payed &&
          other.savedOnlyLocally == this.savedOnlyLocally);
}

class ExpensesCompanion extends UpdateCompanion<Expense> {
  final Value<String> id;
  final Value<String> name;
  final Value<double> sum;
  final Value<ExpenseType?> type;
  final Value<DateTime> date;
  final Value<DateTime?> dueOn;
  final Value<bool> payed;
  final Value<bool> savedOnlyLocally;
  const ExpensesCompanion({
    this.id = const Value.absent(),
    this.name = const Value.absent(),
    this.sum = const Value.absent(),
    this.type = const Value.absent(),
    this.date = const Value.absent(),
    this.dueOn = const Value.absent(),
    this.payed = const Value.absent(),
    this.savedOnlyLocally = const Value.absent(),
  });
  ExpensesCompanion.insert({
    required String id,
    required String name,
    required double sum,
    this.type = const Value.absent(),
    this.date = const Value.absent(),
    this.dueOn = const Value.absent(),
    this.payed = const Value.absent(),
    this.savedOnlyLocally = const Value.absent(),
  })  : id = Value(id),
        name = Value(name),
        sum = Value(sum);
  static Insertable<Expense> custom({
    Expression<String>? id,
    Expression<String>? name,
    Expression<double>? sum,
    Expression<ExpenseType?>? type,
    Expression<DateTime>? date,
    Expression<DateTime?>? dueOn,
    Expression<bool>? payed,
    Expression<bool>? savedOnlyLocally,
  }) {
    return RawValuesInsertable({
      if (id != null) 'id': id,
      if (name != null) 'name': name,
      if (sum != null) 'sum': sum,
      if (type != null) 'type': type,
      if (date != null) 'date': date,
      if (dueOn != null) 'due_on': dueOn,
      if (payed != null) 'payed': payed,
      if (savedOnlyLocally != null) 'saved_only_locally': savedOnlyLocally,
    });
  }

  ExpensesCompanion copyWith(
      {Value<String>? id,
      Value<String>? name,
      Value<double>? sum,
      Value<ExpenseType?>? type,
      Value<DateTime>? date,
      Value<DateTime?>? dueOn,
      Value<bool>? payed,
      Value<bool>? savedOnlyLocally}) {
    return ExpensesCompanion(
      id: id ?? this.id,
      name: name ?? this.name,
      sum: sum ?? this.sum,
      type: type ?? this.type,
      date: date ?? this.date,
      dueOn: dueOn ?? this.dueOn,
      payed: payed ?? this.payed,
      savedOnlyLocally: savedOnlyLocally ?? this.savedOnlyLocally,
    );
  }

  @override
  Map<String, Expression> toColumns(bool nullToAbsent) {
    final map = <String, Expression>{};
    if (id.present) {
      map['id'] = Variable<String>(id.value);
    }
    if (name.present) {
      map['name'] = Variable<String>(name.value);
    }
    if (sum.present) {
      map['sum'] = Variable<double>(sum.value);
    }
    if (type.present) {
      final converter = $ExpensesTable.$converter0;
      map['type'] = Variable<int?>(converter.mapToSql(type.value));
    }
    if (date.present) {
      map['date'] = Variable<DateTime>(date.value);
    }
    if (dueOn.present) {
      map['due_on'] = Variable<DateTime?>(dueOn.value);
    }
    if (payed.present) {
      map['payed'] = Variable<bool>(payed.value);
    }
    if (savedOnlyLocally.present) {
      map['saved_only_locally'] = Variable<bool>(savedOnlyLocally.value);
    }
    return map;
  }

  @override
  String toString() {
    return (StringBuffer('ExpensesCompanion(')
          ..write('id: $id, ')
          ..write('name: $name, ')
          ..write('sum: $sum, ')
          ..write('type: $type, ')
          ..write('date: $date, ')
          ..write('dueOn: $dueOn, ')
          ..write('payed: $payed, ')
          ..write('savedOnlyLocally: $savedOnlyLocally')
          ..write(')'))
        .toString();
  }
}

class $ExpensesTable extends Expenses with TableInfo<$ExpensesTable, Expense> {
  final GeneratedDatabase _db;
  final String? _alias;
  $ExpensesTable(this._db, [this._alias]);
  final VerificationMeta _idMeta = const VerificationMeta('id');
  late final GeneratedColumn<String?> id = GeneratedColumn<String?>(
      'id', aliasedName, false,
      typeName: 'TEXT', requiredDuringInsert: true);
  final VerificationMeta _nameMeta = const VerificationMeta('name');
  late final GeneratedColumn<String?> name = GeneratedColumn<String?>(
      'name', aliasedName, false,
      typeName: 'TEXT', requiredDuringInsert: true);
  final VerificationMeta _sumMeta = const VerificationMeta('sum');
  late final GeneratedColumn<double?> sum = GeneratedColumn<double?>(
      'sum', aliasedName, false,
      typeName: 'REAL', requiredDuringInsert: true);
  final VerificationMeta _typeMeta = const VerificationMeta('type');
  late final GeneratedColumnWithTypeConverter<ExpenseType?, int?> type =
      GeneratedColumn<int?>('type', aliasedName, true,
              typeName: 'INTEGER', requiredDuringInsert: false)
          .withConverter<ExpenseType?>($ExpensesTable.$converter0);
  final VerificationMeta _dateMeta = const VerificationMeta('date');
  late final GeneratedColumn<DateTime?> date = GeneratedColumn<DateTime?>(
      'date', aliasedName, false,
      typeName: 'INTEGER',
      requiredDuringInsert: false,
      defaultValue: Constant(DateTime.now()));
  final VerificationMeta _dueOnMeta = const VerificationMeta('dueOn');
  late final GeneratedColumn<DateTime?> dueOn = GeneratedColumn<DateTime?>(
      'due_on', aliasedName, true,
      typeName: 'INTEGER', requiredDuringInsert: false);
  final VerificationMeta _payedMeta = const VerificationMeta('payed');
  late final GeneratedColumn<bool?> payed = GeneratedColumn<bool?>(
      'payed', aliasedName, false,
      typeName: 'INTEGER',
      requiredDuringInsert: false,
      defaultConstraints: 'CHECK (payed IN (0, 1))',
      defaultValue: const Constant(false));
  final VerificationMeta _savedOnlyLocallyMeta =
      const VerificationMeta('savedOnlyLocally');
  late final GeneratedColumn<bool?> savedOnlyLocally = GeneratedColumn<bool?>(
      'saved_only_locally', aliasedName, false,
      typeName: 'INTEGER',
      requiredDuringInsert: false,
      defaultConstraints: 'CHECK (saved_only_locally IN (0, 1))',
      defaultValue: const Constant(false));
  @override
  List<GeneratedColumn> get $columns =>
      [id, name, sum, type, date, dueOn, payed, savedOnlyLocally];
  @override
  String get aliasedName => _alias ?? 'expenses';
  @override
  String get actualTableName => 'expenses';
  @override
  VerificationContext validateIntegrity(Insertable<Expense> instance,
      {bool isInserting = false}) {
    final context = VerificationContext();
    final data = instance.toColumns(true);
    if (data.containsKey('id')) {
      context.handle(_idMeta, id.isAcceptableOrUnknown(data['id']!, _idMeta));
    } else if (isInserting) {
      context.missing(_idMeta);
    }
    if (data.containsKey('name')) {
      context.handle(
          _nameMeta, name.isAcceptableOrUnknown(data['name']!, _nameMeta));
    } else if (isInserting) {
      context.missing(_nameMeta);
    }
    if (data.containsKey('sum')) {
      context.handle(
          _sumMeta, sum.isAcceptableOrUnknown(data['sum']!, _sumMeta));
    } else if (isInserting) {
      context.missing(_sumMeta);
    }
    context.handle(_typeMeta, const VerificationResult.success());
    if (data.containsKey('date')) {
      context.handle(
          _dateMeta, date.isAcceptableOrUnknown(data['date']!, _dateMeta));
    }
    if (data.containsKey('due_on')) {
      context.handle(
          _dueOnMeta, dueOn.isAcceptableOrUnknown(data['due_on']!, _dueOnMeta));
    }
    if (data.containsKey('payed')) {
      context.handle(
          _payedMeta, payed.isAcceptableOrUnknown(data['payed']!, _payedMeta));
    }
    if (data.containsKey('saved_only_locally')) {
      context.handle(
          _savedOnlyLocallyMeta,
          savedOnlyLocally.isAcceptableOrUnknown(
              data['saved_only_locally']!, _savedOnlyLocallyMeta));
    }
    return context;
  }

  @override
  Set<GeneratedColumn> get $primaryKey => {id};
  @override
  Expense map(Map<String, dynamic> data, {String? tablePrefix}) {
    return Expense.fromData(data, _db,
        prefix: tablePrefix != null ? '$tablePrefix.' : null);
  }

  @override
  $ExpensesTable createAlias(String alias) {
    return $ExpensesTable(_db, alias);
  }

  static TypeConverter<ExpenseType?, int> $converter0 =
      const EnumIndexConverter<ExpenseType>(ExpenseType.values);
}

abstract class _$AppDatabase extends GeneratedDatabase {
  _$AppDatabase(QueryExecutor e) : super(SqlTypeSystem.defaultInstance, e);
  late final $ExpensesTable expenses = $ExpensesTable(this);
  @override
  Iterable<TableInfo> get allTables => allSchemaEntities.whereType<TableInfo>();
  @override
  List<DatabaseSchemaEntity> get allSchemaEntities => [expenses];
}
