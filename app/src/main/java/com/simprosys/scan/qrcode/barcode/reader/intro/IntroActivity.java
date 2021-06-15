package com.simprosys.scan.qrcode.barcode.reader.intro;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.database.query.Query;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable;
import com.simprosys.scan.qrcode.barcode.reader.utility.DatabaseHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.goToActivity;

public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R.id.txtSkip)
    TextView txtSkip;

    @BindView(R.id.txtDone)
    TextView txtDone;

    private PagerAdapter mPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    Class TAG = IntroActivity.class;

    boolean isSetting = false;
    DatabaseHelper oldDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);


        if (doesDatabaseExist(IntroActivity.this, "QRCode")) {
            oldDatabase = new DatabaseHelper(IntroActivity.this);
            oldDatabase.open(DatabaseHelper.WRITE_MODE);
            new UpdateOldDatabase().execute();
            Log.e("database", "Exist");
        }
        if (!SharedPreferenceHelper.getSharedPreferenceString(IntroActivity.this, "splash", "").equals("true")) {
            SharedPreferenceHelper.setSharedPreferenceString(IntroActivity.this, "splash", "true");
        } else {

            if (getIntent().hasExtra("isSetting")) {
                isSetting = getIntent().getBooleanExtra("isSetting", false);
            } else {
                goToActivity(IntroActivity.this, HomeActivity.class);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();
            }
        }


        txtDone.setVisibility(View.GONE);

        fragments.add(IntroFragment.newInstance(getResources().getString(R.string.intro_slide_title_1),
                R.drawable.intro_slide_img_1, getResources().getString(R.string.intro_slide_detail_1)));
        fragments.add(IntroFragment.newInstance(getResources().getString(R.string.intro_slide_title_2),
                R.drawable.intro_slide_img_2, getResources().getString(R.string.intro_slide_detail_2)));
        fragments.add(IntroFragment.newInstance(getResources().getString(R.string.intro_slide_title_3),
                R.drawable.intro_slide_img_3, getResources().getString(R.string.intro_slide_detail_3)));
        fragments.add(IntroFragment.newInstance(getResources().getString(R.string.intro_slide_title_4),
                R.drawable.intro_slide_img_4, getResources().getString(R.string.intro_slide_detail_4)));

        mPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mPagerAdapter);

        circlePageIndicator.setOnPageChangeListener(this);
        circlePageIndicator.setViewPager(viewPager);

        if (isSetting) {
            txtSkip.setVisibility(View.VISIBLE);
            txtDone.setVisibility(View.GONE);
        } else {
            txtSkip.setVisibility(View.GONE);
            txtDone.setVisibility(View.VISIBLE);
        }

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Utility.stopClick())
                    return;

                Utility.goToActivity(IntroActivity.this, HomeActivity.class);

                overridePendingTransition(R.anim.enter, R.anim.exit);

                finish();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utility.stopClick())
                    return;

                Utility.goToActivity(IntroActivity.this, HomeActivity.class);

                overridePendingTransition(R.anim.enter, R.anim.exit);

                finish();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    private class IntroPagerAdapter extends FragmentStatePagerAdapter {
        IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    private class UpdateOldDatabase extends AsyncTask<String, Void, Bitmap> {

        private HistoryTable historyTable;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.showDefaultProgress(IntroActivity.this, getResources().getString(R.string.msgPbBarCode));
        }

        protected Bitmap doInBackground(String... urls) {
            Cursor cursor = oldDatabase.selectQuery("Select * from History order by id");
            Log.e("Count", cursor.getCount() + "");
            while (cursor.moveToNext()) {

                //Gson gson = new Gson();



               /* bId.add(cursor.getString(0));
                bType.add(cursor.getString(1));
                bDate.add(cursor.getString(3));
                bDetails.add(cursor.getString(4));*/
                try {
                    if (cursor.getString(3) != null && cursor.getBlob(2) != null) {
                        byte[] blob = cursor.getBlob(2);
                        String json = new String(blob);
                        Gson gson = new Gson();
                        Result resultData = gson.fromJson(json, Result.class);
                        Date date = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        try {
                            date = sdf.parse(cursor.getString(3));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long timeStamp = 0;
                        if (date != null)
                            timeStamp = date.getTime();
                        else
                            timeStamp = Utility.getCurrantTimeStamp();
                        ParsedResult parsedResult = ResultParser.parseResult(resultData);
                        historyTable = new HistoryTable(parsedResult.getType().name(), resultData.getText(), resultData.getBarcodeFormat().name(), timeStamp, gson.toJson(resultData).getBytes());

                        Query.historySave(historyTable);
                        Query.historyShow();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            cursor.close();
            oldDatabase.close();
            return null;
        }

        protected void onPostExecute(Bitmap result) {

            Utility.removeProgress();
            deleteDatabase(IntroActivity.this, "QRCode");


        }
    }

    public void deleteDatabase(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        dbFile.delete();
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
