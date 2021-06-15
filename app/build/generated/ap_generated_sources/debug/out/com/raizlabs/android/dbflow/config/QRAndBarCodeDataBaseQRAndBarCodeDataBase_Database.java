package com.raizlabs.android.dbflow.config;

import com.simprosys.scan.qrcode.barcode.reader.database.QRAndBarCodeDataBase;
import com.simprosys.scan.qrcode.barcode.reader.database.table.CreateQRCodeTable_Table;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable_Table;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class QRAndBarCodeDataBaseQRAndBarCodeDataBase_Database extends DatabaseDefinition {
  public QRAndBarCodeDataBaseQRAndBarCodeDataBase_Database(DatabaseHolder holder) {
    addModelAdapter(new CreateQRCodeTable_Table(this), holder);
    addModelAdapter(new HistoryTable_Table(this), holder);
  }

  @Override
  public final Class<?> getAssociatedDatabaseClassFile() {
    return QRAndBarCodeDataBase.class;
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "QRAndBarCodeDataBase";
  }
}
