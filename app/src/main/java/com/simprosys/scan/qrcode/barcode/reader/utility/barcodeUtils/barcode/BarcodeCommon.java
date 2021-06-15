package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simprosys on 8/3/19.
 */

public abstract class BarcodeCommon {
    private String rawData = "";
    private List<String> mListErrors = new ArrayList<>();

    protected void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return rawData;
    }

    public List<String> getErrors() {
        return mListErrors;
    }

    protected void error(String ErrorMessage) {
        mListErrors.add(ErrorMessage);
        Log.e("TAG_", ErrorMessage);
        throw new RuntimeException(ErrorMessage);
    }

    public void clearErrors() {
        mListErrors.clear();
    }

    protected static boolean checkNumericOnly(String data) {
        if (data != null) {
            try {
                Long.parseLong(data);
                return true;
            } catch (NumberFormatException ignored) {

            }
        } else {
            return false;
        }

        int lengths = 18;
        String temp = data;
        String piece;

        while (!temp.isEmpty()) {
            if (temp.length() >= lengths) {
                piece = temp.substring(0, lengths);
                temp = temp.substring(lengths);
            } else {
                piece = temp;
            }

            try {
                Long.parseLong(piece);
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
        return true;
    }
}
