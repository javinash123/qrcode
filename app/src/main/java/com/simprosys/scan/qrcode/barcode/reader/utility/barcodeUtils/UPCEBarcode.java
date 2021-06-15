package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils;

import android.content.Context;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.BarcodeCommon;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode.IBarcode;

/**
 * UPC-E encoding
 */
public class UPCEBarcode extends BarcodeCommon implements IBarcode {
    private String[] EAN_CodeA = {"0001101", "0011001", "0010011", "0111101", "0100011", "0110001", "0101111", "0111011", "0110111", "0001011"};
    private String[] EAN_CodeB = {"0100111", "0110011", "0011011", "0100001", "0011101", "0111001", "0000101", "0010001", "0001001", "0010111"};
    private String[] UPCE_Code_0 = {"bbbaaa", "bbabaa", "bbaaba", "bbaaab", "babbaa", "baabba", "baaabb", "bababa", "babaab", "baabab"};
    private String[] UPCE_Code_1 = {"aaabbb", "aababb", "aabbab", "aabbba", "abaabb", "abbaab", "abbbaa", "ababab", "ababba", "abbaba"};
    private Context context;

    public UPCEBarcode(String input, Context context) {
        setRawData(input);
        this.context = context;
    }

    private String encodeUPCE() {
        if (getRawData().length() != 6 && getRawData().length() != 8 && getRawData().length() != 12) {
            error(context.getResources().getString(R.string.msgUPCEBarcode1));
        }

        if (!checkNumericOnly(getRawData())) {
            error(context.getResources().getString(R.string.msgUPCEBarcode2));
        }

        int numberSystem = Integer.parseInt(String.valueOf(getRawData().toCharArray()[0]));
        if (numberSystem != 0 && numberSystem != 1) {
            error(context.getResources().getString(R.string.msgUPCEBarcode3));
        }

        int checkDigit = Integer.parseInt(String.valueOf(getRawData().toCharArray()[getRawData().length() - 1]));

        if (getRawData().length() == 12) {
            String UPCECode = "";

            String manufacturer = getRawData().substring(1, 6);
            String productCode = getRawData().substring(6, 11);

            if (manufacturer.endsWith("000") || manufacturer.endsWith("100") || manufacturer.endsWith("200") && Integer.parseInt(productCode) <= 999) {
                UPCECode += manufacturer.substring(0, 2);
                UPCECode += productCode.substring(2, 5);
                UPCECode += String.valueOf(manufacturer.toCharArray()[2]);
            } else if (manufacturer.endsWith("00") && Integer.parseInt(productCode) <= 99) {
                UPCECode += manufacturer.substring(0, 3);
                UPCECode += productCode.substring(3, 5);
                UPCECode += "3";
            } else if (manufacturer.endsWith("0") && Integer.parseInt(productCode) <= 9) {
                UPCECode += manufacturer.substring(0, 4);
                UPCECode += productCode.toCharArray()[4];
                UPCECode += "4";
            } else if (!manufacturer.endsWith("0") && Integer.parseInt(productCode) <= 9 && Integer.parseInt(productCode) >= 5) {
                UPCECode += manufacturer; //manufacturer
                UPCECode += productCode.toCharArray()[4]; //last digit of product
            } else {
                error(context.getResources().getString(R.string.msgUPCEBarcode4));
            }
            setRawData(UPCECode);
        }

        String pattern;

        if (numberSystem == 0) {
            pattern = UPCE_Code_0[checkDigit];
        } else {
            pattern = UPCE_Code_1[checkDigit];
        }

        StringBuilder result = new StringBuilder("101");

        int pos = 0;
        for (char c : pattern.toCharArray()) {
            int i = Integer.parseInt(String.valueOf(getRawData().toCharArray()[pos++]));
            if (c == 'a') {
                result.append(EAN_CodeA[i]);
            } else if (c == 'b') {
                result.append(EAN_CodeB[i]);
            }
        }

        result.append("01010");

        result.append("1");

        return result.toString();
    }

    public String getEncodedValue() {
        return encodeUPCE();
    }
}