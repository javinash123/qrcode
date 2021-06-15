package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.database.query.Query;
import com.simprosys.scan.qrcode.barcode.reader.database.table.CreateQRCodeTable;
import com.simprosys.scan.qrcode.barcode.reader.fragment.BaseFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Created by simprosys on 29/1/19.
 */

public class Utility extends BaseFragment{
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;

    private static ProgressDialog mProgressDialog;
    private static Spannable spannable;
    private static CreateQRCodeTable createQRCodeTable;
    public static Unregistrar mUnregistrar;
    public static Result qrCodeResult;

    public static boolean isKeyboardOpen = false;
    public static boolean isNewResult = true;

    public static int white = 0xFFFFFFFF;
    public static int black = 0xFF000000;

    private static Class TAG = Utility.class;

    public static void logE(Class tag, String message) {
        Log.e(tag.getSimpleName(), message);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void goToActivity(Context context, Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    public static void setTextColorSpan(String text, int color, int start, int end, TextView textView) {
        spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), start, end, Spannable
                .SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannable);
    }

    public static void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(color);
        }
    }

    public static void setStatusBarColor2(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
            activity.getWindow().setStatusBarColor(color);
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void HideStatusBar(HomeActivity activity) {
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void ShowStatusBar(HomeActivity activity) {
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            // Show Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void removeUnderlines(Spannable spanText) {
        URLSpan[] spans = spanText.getSpans(0, spanText.length(), URLSpan.class);

        for (URLSpan span : spans) {
            int start = spanText.getSpanStart(span);
            int end = spanText.getSpanEnd(span);
            spanText.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            spanText.setSpan(span, start, end, 0);
        }
    }

    public static void showProgress(Context context) {
        mProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.progress_layout);
    }

    public static void removeProgress() {
        mProgressDialog.dismiss();
    }

    public static void showDefaultProgress(Context context, String message){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(context).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(fetchPrimaryColor(context), PorterDuff.Mode.SRC_IN);
            mProgressDialog.setIndeterminateDrawable(drawable);
        }else {
            Drawable drawable = new ProgressBar(context).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(fetchPrimaryColor(context), PorterDuff.Mode.SRC_IN);
            mProgressDialog.setIndeterminateDrawable(drawable);
        }

        mProgressDialog.show();
    }

    public static void removeDefaultProgress() {
        mProgressDialog.dismiss();
    }

    public static File getDirectoryFilePath(Context context, String folderName, String fileName) {
        String root = Environment.getExternalStorageDirectory().toString();

        File fileDir = new File(root + "/" + context.getResources().getString(R.string.appName) + "/" + folderName);
        // File fileDir = new File(root + "/" + context.getResources().getString(R.string.app_name) + "/" + folderName);

        if (!fileDir.exists())
            fileDir.mkdir();

        Log.e("File ", "File Path " + fileDir.getAbsolutePath());
        File file = new File(fileDir, fileName);

        file.getParentFile().mkdirs();

        return file;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long milliSeconds) {
        SimpleDateFormat mFormat = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        String date = mFormat.format(calendar.getTime());

        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat mFormat = new SimpleDateFormat(dateFormat);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        String date = mFormat.format(calendar.getTime());

        return date;
    }

    public static String getDateGMT(long milliSeconds) {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        mFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        String date = mFormat.format(calendar.getTime());

        return date;
    }

    public static long getCurrantTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static String getTimeStampToDate(long timeStamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeStamp);
        String timeString = DateFormat.format("dd-MM-yyyy hh:mm a", cal).toString();
        return timeString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        String dateAndTime = mFormat.format(calendar.getTime());

        return dateAndTime;
    }

    public static boolean isValidPhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() >= 6 && phone.length() <= 13;
        } else {
            check = false;
        }
        return check;
    }

    public static void setQrCodeData(String detail, String type, String formatCodeType) {
        createQRCodeTable = new CreateQRCodeTable(type, detail, formatCodeType, Utility.getCurrantTimeStamp());
        Query.createQrCodeSave(createQRCodeTable);
        Query.createQrCodeShow();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void imgDoneEnableAndDisable(EditText editText, ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().length() > 0) {
                    imageView.setAlpha(1f);
                    imageView.setEnabled(true);
                } else {
                    imageView.setAlpha(0.3f);
                    imageView.setEnabled(false);
                }
            }
        });
    }

    public static void keyboardVisibility(HomeActivity activity) {
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(activity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                   isKeyboardOpen = true;
                    activity.bottomNavViewEx.setVisibility(View.GONE);
                } else {
                    isKeyboardOpen = false;
                    activity.bottomNavViewEx.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void errorMessageDialogShow(HomeActivity activity, String message) {
        new MaterialDialog.Builder(activity)
                .title(R.string.appName)
                .content(message)
                .positiveText(R.string.event_msg_okay)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    public static String qrCodeRead(HomeActivity activity, Bitmap bitmap) {
        String contents = null;

        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        LuminanceSource mSource = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
        BinaryBitmap mBitmap = new BinaryBitmap(new HybridBinarizer(mSource));

        Reader reader = new MultiFormatReader();

        try {
            Result result = reader.decode(mBitmap);
            contents = result.getText();
            qrCodeResult = result;
        } catch (NotFoundException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        } catch (ChecksumException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        } catch (FormatException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        }
        return contents;
    }

    public static void goToActionPlatform(Context context, String actionView, Uri uri) {
        Intent intent = new Intent(actionView, uri);
        context.startActivity(intent);
    }

    public static void setToastMessage(HomeActivity activity, String message, int length) {
        if (length == 0)
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void setBundleFragment(Fragment fragment, String key, String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        assert fragment != null;
        fragment.setArguments(bundle);
    }

    public static void setBundleFragment(Fragment fragment, Bundle bundle) {
        assert fragment != null;

        if (bundle != null) {
            fragment.setArguments(bundle);
        }
    }

    public static BarcodeFormat convertToZXingFormat(int format) {
        switch (format) {
            case 8:
                return BarcodeFormat.CODABAR;
            case 1:
                return BarcodeFormat.CODE_128;
            case 2:
                return BarcodeFormat.CODE_39;
            case 4:
                return BarcodeFormat.CODE_93;
            case 32:
                return BarcodeFormat.EAN_13;
            case 64:
                return BarcodeFormat.EAN_8;
            case 128:
                return BarcodeFormat.ITF;
            case 512:
                return BarcodeFormat.UPC_A;
            case 1024:
                return BarcodeFormat.UPC_E;
            //default 128?
            default:
                return BarcodeFormat.CODE_128;
        }
    }

    public static Bitmap encodeAsBitmapForQrAndBarCode(String contents) {
        BitMatrix bitMatrix;
        Bitmap bitmap = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(contents, Utility.qrCodeResult.getBarcodeFormat(), WIDTH, HEIGHT, null);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? black : white;
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 500, 0, 0, width, height);
        } catch (Exception e) {
            e.printStackTrace();
            Utility.logE(TAG, e.getMessage() + " ");
            return null;
        }
        return bitmap;
    }

    public static String imageQrRead(HomeActivity activity, Bitmap bitmap) {
        String contents = null;

        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        LuminanceSource mSource = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
        BinaryBitmap mBitmap = new BinaryBitmap(new HybridBinarizer(mSource));

        Reader reader = new MultiFormatReader();

        try {
            Result result = reader.decode(mBitmap);
            contents = result.getText();
            Utility.qrCodeResult = result;
            Utility.isNewResult = true;

            Fragment fragment = CreateQRCodeResultFragment.newInstance("ScanResultData");

            activity.pushFragment(fragment);

            SharedPreferenceHelper.setSharedPreferenceString(Constant.activity, "Fragment", "CreateQRCodeResultForGallery");
        } catch (NotFoundException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        } catch (ChecksumException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        } catch (FormatException e) {
            e.printStackTrace();
            //setToastMessage(activity, "Can not read from image", 0);
        }

        return contents;
    }

    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static String saveImageToGallery(HomeActivity activity, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        try {
            File file = Utility.getDirectoryFilePath(activity, "Save", Calendar.getInstance().getTimeInMillis() + ".jpg");

            file.createNewFile();
            FileOutputStream mStream = new FileOutputStream(file);
            mStream.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity, new String[]{file.getPath()}, new String[]{"image/jpeg"}, null);
            mStream.close();

            return file.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public static boolean barCodeChecksum(CharSequence s) throws FormatException {
        int length = s.length();

        if (length == 0) {
            return false;
        }

        int sum = 0;
        for (int i = length - 2; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }

        sum *= 3;

        for (int i = length - 1; i >= 0; i -= 2) {
            int digit = (int) s.charAt(i) - (int) '0';
            if (digit < 0 || digit > 9) {
                throw FormatException.getFormatInstance();
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }

    public static int fetchPrimaryColor(Context context){
        TypedValue typedValue = new TypedValue();

        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = typedArray.getColor(0, 0);

        typedArray.recycle();

        return color;
    }

    public static String getChar(int i) {
        return String.valueOf(Character.forDigit(i, 10));
    }

    public static void goToRedirectForBrowser(Activity activity, Uri uri){
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static boolean stopClick() {
        if (SystemClock.elapsedRealtime() - Constant.LAST_CLICK_TIME < 1000) {
            return false;
        }
        Constant.LAST_CLICK_TIME = SystemClock.elapsedRealtime();
        return true;
    }

}
