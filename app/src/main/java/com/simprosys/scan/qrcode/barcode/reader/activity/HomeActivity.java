package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.zxing.Result;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.fragment.BaseFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.HistoryFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.ScannerFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.SettingFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Constant;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.simprosys.scan.qrcode.barcode.reader.view.FragNavController;
import com.simprosys.scan.qrcode.barcode.reader.view.FragmentHistory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    public static Result result;

    final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase("themeRefresh")) {

                HomeActivity.this.recreate();


            }
        }
    };

    public boolean isBack = false;

    @BindView(R.id.bottomNavViewEx)
    public BottomNavigationViewEx bottomNavViewEx;
    public int lastId = 0;
    @BindView(R.id.frmContainer)
    FrameLayout frmContainer;
    Fragment scannerFragment, createQRCodeFragment, historyFragment, settingFragment;
    HomeActivity activity;
    private FragNavController mNavController;
    private FragmentHistory fragmentHistory;
    private Class TAG = HomeActivity.class;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("themeRefresh"));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUpBottomNavigationView();

        fragmentHistory = new FragmentHistory();

        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.frmContainer)
                .transactionListener(this)
                .rootFragmentListener(this, bottomNavViewEx.getItemCount())
                .build();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bottomNavViewEx.getCurrentItem() != 0) {
                    bottomNavViewEx.setCurrentItem(bottomNavViewEx.getCurrentItem());
                    switchTab(bottomNavViewEx.getCurrentItem());
                    updateTabSelection(bottomNavViewEx.getCurrentItem());
                }
            }
        }, 50);

        addFragmentList();
    }

    private void addFragmentList() {
        scannerFragment = new ScannerFragment();
        createQRCodeFragment = new CreateQRCodeFragment();
        historyFragment = new HistoryFragment();
        settingFragment = new SettingFragment();
        ScannerFragment.getInstance().checkPermissionCamera();
        bottomNavViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (!Utility.stopClick()) {
                    return false;
                }

                Utility.logE(TAG, "Selected Tab" + item.getItemId());

                if (lastId == item.getItemId()) {
                    mNavController.clearStack();
                    return true;
                } else {
                    switch (item.getItemId()) {
                        case R.id.navScan:
                            if (!isBack) {
                                fragmentHistory.push(0);
                            }
                            switchTab(0);
                            lastId = item.getItemId();

                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            //Utility.HideStatusBar(HomeActivity.this);
                            if (CreateQRCodeResultFragment.getInstance() != null) {
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            }

                            if (ScannerFragment.getInstance() != null) {
                                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            }

                            if (ScannerFragment.getInstance() != null) {
                                Log.e("instance", "not null");
                                ScannerFragment.getInstance().checkPermissionCamera();
                            }

                            break;

                        case R.id.navCreate:
                            if (!isBack) {
                                fragmentHistory.push(1);
                            }
                            switchTab(1);
                            lastId = item.getItemId();

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                            //Utility.ShowStatusBar(HomeActivity.this);
                            break;

                        case R.id.navHistory:
                            if (!isBack) {
                                fragmentHistory.push(2);
                            }
                            switchTab(2);
                            lastId = item.getItemId();

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            //Utility.ShowStatusBar(HomeActivity.this);
                            break;

                        case R.id.navSetting:
                            if (!isBack) {
                                fragmentHistory.push(3);
                            }
                            switchTab(3);
                            lastId = item.getItemId();

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            //Utility.ShowStatusBar(HomeActivity.this);
                            break;
                    }

                    isBack = false;
                    return true;
                }
            }
        });
    }

    private void setUpBottomNavigationView() {
        bottomNavViewEx.enableAnimation(false);
        bottomNavViewEx.enableItemShiftingMode(false);
        bottomNavViewEx.enableShiftingMode(false);
        bottomNavViewEx.setTextVisibility(true);
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
    }


    private void updateTabSelection(int currentTab) {
        bottomNavViewEx.setCurrentItem(currentTab);
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("themeRefresh"));
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            Log.e("not root", "yes");
            //mNavController.popFragment();
            if (Constant.isPopFragment == 0) {
                mNavController.popFragment();
            } else if (Constant.isPopFragment == 1) {
                mNavController.clearStack();
            } else {
                mNavController.clearStack();
            }
        } else {
            if (fragmentHistory.isEmpty()) {
                Log.e("history", "empty");
                //super.onBackPressed();
                if (SharedPreferenceHelper.getSharedPreferenceBoolean(HomeActivity.this, "isReviewed", false)) {
                    finish();
                } else {
                    showReviewDialog();
                }
            } else {
                Log.e("stack size", fragmentHistory.getStackSize() + "");
                if (fragmentHistory.getStackSize() > 1) {
                    isBack = true;

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                    updateTabSelection(position);

                } else {
                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();
                    //super.onBackPressed();
                }
            }
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case FragNavController.TAB1:
                return new ScannerFragment();
            case FragNavController.TAB2:
                return new CreateQRCodeFragment();
            case FragNavController.TAB3:
                return new HistoryFragment();
            case FragNavController.TAB4:
                return new SettingFragment();
            case FragNavController.TAB5:
                break;
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

    }

    private void showReviewDialog() {
        try {

            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
            alert.setTitle(getResources().getString(R.string.txtRateUs));
            alert.setMessage(
                    getResources().getString(R.string.txtRateDescription));
            alert.setPositiveButton(getResources().getString(R.string.txtRate), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    SharedPreferenceHelper.setSharedPreferenceBoolean(HomeActivity.this, "isReviewed", true);
                }
            });
            alert.setNegativeButton(getResources().getString(R.string.txtClose), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //SharedPreferenceHelper.setSharedPreferenceBoolean(HomeActivity.this,"isReviewed",true);
                    finish();
                }
            });
            alert.show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }
}
