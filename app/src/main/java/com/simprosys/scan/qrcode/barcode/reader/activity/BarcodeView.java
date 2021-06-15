package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code128;
import com.onbarcode.barcode.android.Code39;
import com.onbarcode.barcode.android.EAN13;
import com.onbarcode.barcode.android.EAN8;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.UPCA;
import com.onbarcode.barcode.android.UPCE;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

/**
 * Created by simprosys on 11/3/19.
 */

public class BarcodeView extends View {
    private String codeData;
    private String codeFormat;

    private Class TAG = BarcodeView.class;

    public BarcodeView(Context context, String codeData, String codeFormat) {
        super(context);
        this.codeFormat = codeFormat;
        this.codeData = codeData;
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BarcodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private static void setEAN13(Canvas canvas, String content) throws Exception {
        String firstChar = content.substring(0, 12);
        String lastChar = content.substring(content.length() - 1);

        EAN13 barcode = new EAN13();

        barcode.setData(firstChar);
        barcode.setSupData(lastChar);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(200f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);
        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextColor(AndroidColor.black);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));

        RectF bounds = new RectF(80, 140, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    private static void setEAN8(Canvas canvas, String content) throws Exception {
        String firstChar = content.substring(0, 7);
        String lastChar = content.substring(content.length() - 1);

        EAN8 barcode = new EAN8();

        barcode.setData(firstChar);
        barcode.setSupData(lastChar);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(200f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);
        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextColor(AndroidColor.black);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));

        RectF bounds = new RectF(100, 140, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    private static void setUPCE(Canvas canvas, String content) throws Exception {
        String firstChar = content.substring(0, 6);
        String lastChar = content.substring(content.length() - 2);

        UPCE barcode = new UPCE();

        barcode.setData(firstChar);
        barcode.setSupData(lastChar);
        barcode.setSupSpace(100);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(200f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);
        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextColor(AndroidColor.black);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));

        RectF bounds = new RectF(100, 140, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    private static void setCODE39(Canvas canvas, String content) throws Exception {
        Code39 barcode = new Code39();

        barcode.setData(content);
        barcode.setExtension(false);
        barcode.setAddCheckSum(true);

        barcode.setN(3.0f);
        barcode.setI(1.0f);
        barcode.setShowStartStopInText(true);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(100f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);

        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));
        barcode.setTextColor(AndroidColor.black);

        RectF bounds = new RectF(20, 180, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    private static void setCODE128(Canvas canvas, String content) throws Exception {
        Code128 barcode = new Code128();

        barcode.setData(content);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(2.5f);
        barcode.setY(100f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);

        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));
        barcode.setTextColor(AndroidColor.black);

        RectF bounds = new RectF(50, 180, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    private static void setUPCA(Canvas canvas, String content) throws Exception {
        String firstChar = content.substring(0, 11);
        String lastChar = content.substring(content.length() - 1);

        UPCA barcode = new UPCA();

        barcode.setData(firstChar);
        barcode.setSupData(lastChar);

        barcode.setUom(IBarcode.UOM_PIXEL);
        barcode.setX(3f);
        barcode.setY(200f);

        barcode.setLeftMargin(10f);
        barcode.setRightMargin(10f);
        barcode.setTopMargin(10f);
        barcode.setBottomMargin(10f);
        barcode.setResolution(100);

        barcode.setShowText(true);
        barcode.setTextColor(AndroidColor.black);
        barcode.setTextFont(new AndroidFont("Arial", Typeface.NORMAL, 30));

        RectF bounds = new RectF(80, 140, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (codeFormat) {
            case "EAN_13":
                try {
                    setEAN13(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;

            case "EAN_8":
                try {
                    setEAN8(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;

            case "UPC_E":
                try {
                    setUPCE(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;

            case "CODE_39":
                try {
                    setCODE39(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;

                case "EAN_128":
                try {
                    setCODE128(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;

                case "UPC_A":
                try {
                    setUPCA(canvas, codeData);
                } catch (Exception e) {
                    e.getMessage();
                    Utility.logE(TAG, e.getMessage() + " ");
                }

                break;
        }
    }
}
