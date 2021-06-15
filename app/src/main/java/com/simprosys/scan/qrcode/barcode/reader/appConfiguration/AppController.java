package com.simprosys.scan.qrcode.barcode.reader.appConfiguration;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import androidx.multidex.MultiDex;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;


/**
 * Created by simprosys on 29/11/18.
 */


public class AppController extends Application {
    private Class TAG = AppController.class;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
        }

        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
        MultiDex.install(this);
    }
}
