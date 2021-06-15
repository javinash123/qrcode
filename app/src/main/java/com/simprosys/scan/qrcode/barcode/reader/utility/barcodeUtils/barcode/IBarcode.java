package com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.barcode;

import java.util.List;

/**
 * Created by simprosys on 8/3/19.
 */

public interface IBarcode {
    String getEncodedValue();
    String getRawData();
    List<String> getErrors();
    void clearErrors();
}
