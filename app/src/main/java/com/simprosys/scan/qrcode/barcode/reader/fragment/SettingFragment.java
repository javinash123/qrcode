package com.simprosys.scan.qrcode.barcode.reader.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.AppsActivity;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.activity.LanguageActivity;
import com.simprosys.scan.qrcode.barcode.reader.activity.ThemeActivity;
import com.simprosys.scan.qrcode.barcode.reader.intro.IntroActivity;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.view.SwitchButton;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.goToRedirectForBrowser;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private static SettingFragment instance = null;

    @BindView(R.id.imgAppIconLeft)
    ImageView imgAppIconLeft;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.sbVibrate)
    SwitchButton sbVibrate;

    @BindView(R.id.sbBeep)
    SwitchButton sbBeep;

    @BindView(R.id.relLanguage)
    RelativeLayout relLanguage;

    @BindView(R.id.relApps)
    RelativeLayout relApps;

    private boolean mVibrate;
    private boolean mBeep;
    private HomeActivity activity;

    private Class TAG = SettingFragment.class;

    public SettingFragment() {

    }

    public static SettingFragment getInstance() {
        return instance;
    }

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        args.putString("NAME", "Setting");
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.imgAppIconLeft)
    public void onClickBack() {
        Utility.hideKeyboard(activity);
        activity.onBackPressed();
    }

    @OnClick(R.id.relTheme)
    public void onClickTheme() {
        if (!Utility.stopClick()) {
            return;
        }

        startActivity(new Intent(activity, ThemeActivity.class));
    }

    @OnClick(R.id.relLanguage)
    public void onClickLanguage() {
        if (!Utility.stopClick()) {
            return;
        }

        startActivity(new Intent(activity, LanguageActivity.class));
    }

    @OnClick(R.id.relApps)
    public void onClickApps() {
        if (!Utility.stopClick()) {
            return;
        }
        Intent intent=new Intent(activity, AppsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.relHelp)
    public void onClickHelp() {
        if (!Utility.stopClick()) {
            return;
        }
        Intent intent =new Intent(activity, IntroActivity.class);
        intent.putExtra("isSetting",true);
        startActivity(intent);
    }

    @OnClick(R.id.relPrivacy)
    public void onClickPrivacy() {
        if(!Utility.stopClick())
            return;
        Uri uri = Uri.parse("http://wisdomlogix.com/app/qrcodescanner/privacy-policy.html");

        goToRedirectForBrowser(activity, uri);
    }

    @OnClick(R.id.relRateUs)
    public void onClickRateUs() {
        if(!Utility.stopClick())
            return;
        Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName())));
        }
    }

//    @OnClick(R.id.relMoreApp)
//    public void onClickMoreApp() {
//        if(!Utility.stopClick())
//            return;
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgAppIconLeft.setVisibility(View.GONE);

        txtAppName.setText(getResources().getString(R.string.txtAppNameSetting));

        setupSetting();

        sbVibrate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    setPreference("Vibrate", true);
                } else {
                    setPreference("Vibrate", false);
                }
            }
        });

        sbBeep.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    setPreference("Beep", true);
                } else {
                    setPreference("Beep", false);
                }
            }
        });
    }

    private void setupSetting() {
        mVibrate = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "Vibrate", true);
        mBeep = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "Beep", true);

        if (mVibrate == true) {
            sbVibrate.setChecked(true);
            sbVibrate.isChecked();
            sbVibrate.toggle();
            sbVibrate.toggle(false);
            sbVibrate.setShadowEffect(true);
            sbVibrate.setEnabled(true);
            sbVibrate.setEnableEffect(true);
        } else {
            sbVibrate.setChecked(false);
            sbVibrate.isChecked();
            sbVibrate.toggle();
            sbVibrate.toggle(false);
            sbVibrate.setShadowEffect(true);
            sbVibrate.setEnabled(true);
            sbVibrate.setEnableEffect(true);
        }

        if (mBeep == true) {
            sbBeep.setChecked(true);
            sbBeep.isChecked();
            sbBeep.toggle();
            sbBeep.toggle(false);
            sbBeep.setShadowEffect(true);
            sbBeep.setEnabled(true);
            sbBeep.setEnableEffect(true);
        } else {
            sbBeep.setChecked(false);
            sbBeep.isChecked();
            sbBeep.toggle();
            sbBeep.toggle(false);
            sbBeep.setShadowEffect(true);
            sbBeep.setEnabled(true);
            sbBeep.setEnableEffect(true);
        }
    }

    private void setPreference(String key, boolean value) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(activity, key, value);
    }
}
