package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.app.Activity;

import com.google.zxing.Result;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;

import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

/**
 * Created by simprosys on 29/1/19.
 */

public class Constant {
    public static final int GET_IMAGE_QR_FROM_GALLERY = 1;
    public static final int GET_IMAGE_FROM_GALLERY = 2;
    public static long LAST_CLICK_TIME = 0;
    public static Activity activity;
    public static int colorPosition;
    public static int isPopFragment;
}
