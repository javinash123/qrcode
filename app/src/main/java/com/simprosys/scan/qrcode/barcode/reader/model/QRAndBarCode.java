package com.simprosys.scan.qrcode.barcode.reader.model;

import com.google.zxing.Result;

import java.io.Serializable;

/**
 * Created by simprosys on 1/2/19.
 */

public class QRAndBarCode implements Serializable {
    private int id;
    private String type;
    private String dateAndTime;
    private String formatType;
    private String details;
    private Result result;

    public QRAndBarCode(int id, String type, String dateAndTime, String details, Result result) {
        this.id = id;
        this.type = type;
        this.dateAndTime = dateAndTime;
        this.details = details;
        this.result = result;
    }

    public QRAndBarCode(int id, String type, String dateAndTime, String formatType, String details) {
        this.id = id;
        this.type = type;
        this.dateAndTime = dateAndTime;
        this.formatType = formatType;
        this.details = details;
    }

    public QRAndBarCode(int id, String type, String dateAndTime, String formatType, String details, Result result) {
        this.id = id;
        this.type = type;
        this.dateAndTime = dateAndTime;
        this.formatType = formatType;
        this.details = details;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "QRAndBarCode{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", formatType='" + formatType + '\'' +
                ", details='" + details + '\'' +
                ", result=" + result +
                '}';
    }
}

