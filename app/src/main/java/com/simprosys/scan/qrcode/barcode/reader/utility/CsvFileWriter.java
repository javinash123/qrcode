package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable_Table;
import com.simprosys.scan.qrcode.barcode.reader.model.QRAndBarCode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by simprosys on 7/2/19.
 */

public class CsvFileWriter {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "Id, Code Type, Data, ScanTime";

    public static void writeCsvFile(String fileName) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            fileWriter.append(FILE_HEADER);

            fileWriter.append(NEW_LINE_SEPARATOR);

            List<HistoryTable> tableList = SQLite.select().from(HistoryTable.class).orderBy(HistoryTable_Table.id, false).queryList();

            int intSize = tableList.size();

            String resultData;

            if (intSize > 0) {
                for (int i = 0; i < intSize; i++) {
                    fileWriter.append(String.valueOf(tableList.get(i).getId()));
                    fileWriter.append(COMMA_DELIMITER);

                    fileWriter.append(tableList.get(i).getType());
                    fileWriter.append(COMMA_DELIMITER);

                    byte[] bytes = tableList.get(i).getResult();
                    String result = new String(bytes);
                    Gson gson = new Gson();

                    resultData = gson.fromJson(result, Result.class).getText().replace("\n", "").replace(",", "");

                    Log.e("TAG_", "Data Format : " + resultData.replace("URL:http://www.example.comEND:VCARD", "URL:http://www.example.com END:VCARD"));

                    fileWriter.append(resultData);
                    fileWriter.append(COMMA_DELIMITER);

                    fileWriter.append(Utility.getTimeStampToDate(tableList.get(i).getDateAndTime()));
                    fileWriter.append(NEW_LINE_SEPARATOR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
