package com.simprosys.scan.qrcode.barcode.reader.database.table;

import com.google.zxing.Result;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.simprosys.scan.qrcode.barcode.reader.database.QRAndBarCodeDataBase;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by simprosys on 29/11/18.
 */

@Table(database = QRAndBarCodeDataBase.class)

public class HistoryTable extends BaseModel implements Serializable{
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String type;

    @Column
    String details;

    @Column
    String formatType;

    @Column
    long dateAndTime;

    @Column
    byte[] result;

    public HistoryTable() {
    }

    public HistoryTable(String type, String details, String formatType, long dateAndTime, byte[] result) {
        this.type = type;
        this.details = details;
        this.formatType = formatType;
        this.dateAndTime = dateAndTime;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HistoryTable{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", formatType='" + formatType + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", result=" + Arrays.toString(result) +
                '}';
    }
}
