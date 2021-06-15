package com.simprosys.scan.qrcode.barcode.reader.database.table;

import android.content.ContentValues;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.sql.saveable.AutoIncrementModelSaver;
import com.raizlabs.android.dbflow.sql.saveable.ModelSaver;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.FlowCursor;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Number;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class HistoryTable_Table extends ModelAdapter<HistoryTable> {
  /**
   * Primary Key AutoIncrement */
  public static final Property<Integer> id = new Property<Integer>(HistoryTable.class, "id");

  public static final Property<String> type = new Property<String>(HistoryTable.class, "type");

  public static final Property<String> details = new Property<String>(HistoryTable.class, "details");

  public static final Property<String> formatType = new Property<String>(HistoryTable.class, "formatType");

  public static final Property<Long> dateAndTime = new Property<Long>(HistoryTable.class, "dateAndTime");

  public static final Property<byte[]> result = new Property<byte[]>(HistoryTable.class, "result");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,type,details,formatType,dateAndTime,result};

  public HistoryTable_Table(DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<HistoryTable> getModelClass() {
    return HistoryTable.class;
  }

  @Override
  public final String getTableName() {
    return "`HistoryTable`";
  }

  @Override
  public final HistoryTable newInstance() {
    return new HistoryTable();
  }

  @Override
  public final Property getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch ((columnName)) {
      case "`id`":  {
        return id;
      }
      case "`type`":  {
        return type;
      }
      case "`details`":  {
        return details;
      }
      case "`formatType`":  {
        return formatType;
      }
      case "`dateAndTime`":  {
        return dateAndTime;
      }
      case "`result`":  {
        return result;
      }
      default: {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void updateAutoIncrement(HistoryTable model, Number id) {
    model.id = id.intValue();
  }

  @Override
  public final Number getAutoIncrementingId(HistoryTable model) {
    return model.id;
  }

  @Override
  public final String getAutoIncrementingColumnName() {
    return "id";
  }

  @Override
  public final ModelSaver<HistoryTable> createSingleModelSaver() {
    return new AutoIncrementModelSaver<>();
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, HistoryTable model) {
    values.put("`type`", model.type);
    values.put("`details`", model.details);
    values.put("`formatType`", model.formatType);
    values.put("`dateAndTime`", model.dateAndTime);
    values.put("`result`", model.result);
  }

  @Override
  public final void bindToContentValues(ContentValues values, HistoryTable model) {
    values.put("`id`", model.id);
    bindToInsertValues(values, model);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, HistoryTable model,
      int start) {
    statement.bindStringOrNull(1 + start, model.type);
    statement.bindStringOrNull(2 + start, model.details);
    statement.bindStringOrNull(3 + start, model.formatType);
    statement.bindLong(4 + start, model.dateAndTime);
    statement.bindBlobOrNull(5 + start, model.result);
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, HistoryTable model) {
    int start = 0;
    statement.bindLong(1 + start, model.id);
    bindToInsertStatement(statement, model, 1);
  }

  @Override
  public final void bindToUpdateStatement(DatabaseStatement statement, HistoryTable model) {
    statement.bindLong(1, model.id);
    statement.bindStringOrNull(2, model.type);
    statement.bindStringOrNull(3, model.details);
    statement.bindStringOrNull(4, model.formatType);
    statement.bindLong(5, model.dateAndTime);
    statement.bindBlobOrNull(6, model.result);
    statement.bindLong(7, model.id);
  }

  @Override
  public final void bindToDeleteStatement(DatabaseStatement statement, HistoryTable model) {
    statement.bindLong(1, model.id);
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `HistoryTable`(`type`,`details`,`formatType`,`dateAndTime`,`result`) VALUES (?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `HistoryTable`(`id`,`type`,`details`,`formatType`,`dateAndTime`,`result`) VALUES (?,?,?,?,?,?)";
  }

  @Override
  public final String getUpdateStatementQuery() {
    return "UPDATE `HistoryTable` SET `id`=?,`type`=?,`details`=?,`formatType`=?,`dateAndTime`=?,`result`=? WHERE `id`=?";
  }

  @Override
  public final String getDeleteStatementQuery() {
    return "DELETE FROM `HistoryTable` WHERE `id`=?";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `HistoryTable`(`id` INTEGER PRIMARY KEY AUTOINCREMENT, `type` TEXT, `details` TEXT, `formatType` TEXT, `dateAndTime` INTEGER, `result` BLOB)";
  }

  @Override
  public final void loadFromCursor(FlowCursor cursor, HistoryTable model) {
    model.id = cursor.getIntOrDefault("id");
    model.type = cursor.getStringOrDefault("type");
    model.details = cursor.getStringOrDefault("details");
    model.formatType = cursor.getStringOrDefault("formatType");
    model.dateAndTime = cursor.getLongOrDefault("dateAndTime");
    model.result = cursor.getBlobOrDefault("result");
  }

  @Override
  public final boolean exists(HistoryTable model, DatabaseWrapper wrapper) {
    return model.id > 0
    && SQLite.selectCountOf()
    .from(HistoryTable.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final OperatorGroup getPrimaryConditionClause(HistoryTable model) {
    OperatorGroup clause = OperatorGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }
}
