package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.ThemeUtil;


/**
 * Created by Pankaj on 03-11-2017.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int currentTheme = SharedPreferenceHelper.getSharedPreferenceInt(BaseActivity.this, "SelectedTheme", 0);
        setTheme(ThemeUtil.getThemeId(currentTheme));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
