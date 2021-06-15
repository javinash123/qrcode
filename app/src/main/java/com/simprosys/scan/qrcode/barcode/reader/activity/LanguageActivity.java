package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.adapter.LanguageSelectionAdapter;
import com.simprosys.scan.qrcode.barcode.reader.model.Language;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends BaseActivity {
    @BindView(R.id.imgAppIconLeft)
    ImageView imgAppIconLeft;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.recLanguage)
    RecyclerView recLanguage;

    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase("themeRefresh")) {

                String languageCode=intent.getStringExtra("languageCode");
                Log.e("languageCode",languageCode);
                /*LanguageHelper.setLanguage(LanguageActivity.this,languageCode);
                LanguageActivity.this.recreate();*/
                startActivity(new Intent(LanguageActivity.this,LanguageActivity.class));
                finish();


            }
        }
    };

    private LinearLayoutManager mLayoutManager;
    private LanguageSelectionAdapter mAdapter;
    private ArrayList<Language> langAndLocalesList = new ArrayList<>();

    @OnClick(R.id.imgAppIconLeft)
    public void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        ButterKnife.bind(this);

        txtAppName.setText(getResources().getString(R.string.txtAppNameLanguage));

        initView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recLanguage.setLayoutManager(mLayoutManager);
        recLanguage.setItemAnimator(new DefaultItemAnimator());

        loadLanguageList();

        mAdapter = new LanguageSelectionAdapter(this, langAndLocalesList);
        recLanguage.setAdapter(mAdapter);
    }

    private void loadLanguageList() {

        for (String langCode : getResources().getStringArray(R.array.ln)) {
            Locale languageLocale = new Locale(langCode);
            String languageName;

            switch (langCode) {
                case "zh-rCN":
                    languageName = "Chinese (Simplified)";
                    break;
                case "zh-rTW":
                    languageName = "Chinese (Traditional)";
                    break;
                default:
                    languageName = languageLocale.getDisplayLanguage(languageLocale);
                    break;
            }

            Locale englishLocale = new Locale(getString(R.string.en));
            String languageNameInEnglishLocale = languageLocale.getDisplayLanguage(englishLocale);
            Language langAndLocale = new Language(langCode, languageName, languageNameInEnglishLocale);
            langAndLocalesList.add(langAndLocale);

           /* // based on language code get language name to display in same locale
            Locale languageLocale = new Locale(langCode);
            String languageName = languageLocale.getDisplayLanguage(languageLocale);

            // based on language code get language name to display in english locale
            Locale englishLocale = new Locale(getString(R.string.en));
            String languageNameInEnglishLocale = languageLocale.getDisplayLanguage(englishLocale);
            Language langAndLocale = new Language(langCode, languageName, languageNameInEnglishLocale);
            langAndLocalesList.add(langAndLocale);*/
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("themeRefresh"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
