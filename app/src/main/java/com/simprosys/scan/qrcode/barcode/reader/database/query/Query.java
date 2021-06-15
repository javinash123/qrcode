package com.simprosys.scan.qrcode.barcode.reader.database.query;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.simprosys.scan.qrcode.barcode.reader.database.table.CreateQRCodeTable;
import com.simprosys.scan.qrcode.barcode.reader.database.table.CreateQRCodeTable_Table;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable_Table;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.util.List;

/**
 * Created by simprosys on 29/11/18.
 */

public class Query {
    private static Class TAG = Query.class;

    public static void createQrCodeSave(CreateQRCodeTable createQRCodeTable) {
        CreateQRCodeTable createQRCodeTable1 = new CreateQRCodeTable();
        createQRCodeTable1 = createQRCodeTable;
        createQRCodeTable1.save();
        Utility.logE(TAG, "Qr Code Add");
    }

    public static List<CreateQRCodeTable> createQrCodeShow() {
        List<CreateQRCodeTable> mList = SQLite.select().from(CreateQRCodeTable.class).orderBy(CreateQRCodeTable_Table.id, false).queryList();

        int listSize = mList.size();

        //Utility.logE(TAG, "Size Of Qr Code Table = " + listSize);

        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                String listItem = mList.get(i).toString();

                //Utility.logE(TAG, "Index Number Of Data Qr Code : " + i + "=" + listItem);
            }
        }
        return mList;
    }

    public static void createQrCodeDelete(int id) {
        SQLite.delete().from(CreateQRCodeTable.class).
                where(CreateQRCodeTable_Table.id.eq(id)).
                execute();

        Utility.logE(TAG, "Qr Code Remove");
    }

    public static void createQrCodeAllDelete() {
        SQLite.delete().from(CreateQRCodeTable.class).execute();

        Utility.logE(TAG, "All Qr Code Remove");
    }

    public static void historySave(HistoryTable historyTable) {
        HistoryTable historyTable1 = new HistoryTable();
        historyTable1 = historyTable;
        historyTable1.save();
        Utility.logE(TAG, "History Add");
    }

    public static List<HistoryTable> historyShow() {
        List<HistoryTable> mList = SQLite.select().from(HistoryTable.class).orderBy(HistoryTable_Table.id, false).queryList();

        int listSize = mList.size();

        //Utility.logE(TAG, "Size Of History Table = " + listSize);

        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                String listItem = mList.get(i).toString();

                //Utility.logE(TAG, "Index Number Of Data History Item : " + i + "=" + listItem);
            }
        }
        return mList;
    }

    public static void historyDelete(int id) {
        SQLite.delete().from(HistoryTable.class).
                where(HistoryTable_Table.id.eq(id)).
                execute();

        Utility.logE(TAG, "History Item Remove");
    }

    public static void historyAllDelete() {
        SQLite.delete().from(HistoryTable.class).execute();

        Utility.logE(TAG, "All History Item Remove");
    }
}
