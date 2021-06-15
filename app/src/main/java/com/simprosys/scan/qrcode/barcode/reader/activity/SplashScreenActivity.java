package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.intro.IntroActivity;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;

import butterknife.ButterKnife;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.goToActivity;

public class SplashScreenActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 1000;
    private Handler handler;
    private Runnable runnable;

    private Class TAG = SplashScreenActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if (!SharedPreferenceHelper.getSharedPreferenceString(SplashScreenActivity.this, "splash", "").equals("true")) {
                    goToActivity(SplashScreenActivity.this, IntroActivity.class);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();

                    SharedPreferenceHelper.setSharedPreferenceString(SplashScreenActivity.this, "splash", "true");
                } else {
                    goToActivity(SplashScreenActivity.this, HomeActivity.class);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
}
