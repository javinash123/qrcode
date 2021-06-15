package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.adapter.ThemeAdapter;
import com.simprosys.scan.qrcode.barcode.reader.model.Theme;
import com.simprosys.scan.qrcode.barcode.reader.utility.Constant;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.ThemeUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemeActivity extends BaseActivity {
    public static ArrayList<Theme> mItemList = new ArrayList<>();
    public static int selectedTheme;

    @BindView(R.id.imgAppIconLeft)
    ImageView imgAppIconLeft;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.recTheme)
    RecyclerView recTheme;

    private ThemeAdapter mAdapter;

    private Class TAG = ThemeActivity.class;

    @OnClick(R.id.imgAppIconLeft)
    public void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theme);

        ButterKnife.bind(this);

        txtAppName.setText(getResources().getString(R.string.txtAppNameTheme));

        mAdapter = new ThemeAdapter(this, mItemList);

        recTheme.setLayoutManager(new LinearLayoutManager(this));
        recTheme.setHasFixedSize(true);
        recTheme.setItemAnimator(new DefaultItemAnimator());

        recTheme.setAdapter(mAdapter);

        mAdapter.setOnItemClick(new ThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SharedPreferenceHelper.setSharedPreferenceInt(ThemeActivity.this, "SelectedTheme", position);

                ThemeActivity.this.recreate();
                selectedTheme = position;
                Constant.colorPosition = position;

                Intent dataRefresh = new Intent();
                dataRefresh.setAction("themeRefresh");
                LocalBroadcastManager.getInstance(ThemeActivity.this).sendBroadcast(dataRefresh);

            }
        });

        prepareThemeData();
    }

    private void prepareThemeData() {
        mItemList.clear();
        mItemList.addAll(ThemeUtil.getThemeList(ThemeActivity.this));
        mAdapter.notifyDataSetChanged();
    }
}
