package com.simprosys.scan.qrcode.barcode.reader.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by simprosys on 29/11/18.
 */

@Database(name = QRAndBarCodeDataBase.NAME, version = QRAndBarCodeDataBase.VERSION)

public class QRAndBarCodeDataBase {
    public static final String NAME = "QRAndBarCodeDataBase";
    public static final int VERSION = 1;
}
