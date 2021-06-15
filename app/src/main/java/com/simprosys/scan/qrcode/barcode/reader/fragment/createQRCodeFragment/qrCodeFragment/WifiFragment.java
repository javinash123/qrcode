package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WifiFragment extends Fragment {
    private static WifiFragment instance = null;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.rgToggle)
    RadioGroup rgToggle;

    @BindView(R.id.rbWpaWpa2)
    RadioButton rbWpaWpa2;

    @BindView(R.id.rbWep)
    RadioButton rbWep;

    @BindView(R.id.rbNoEncryption)
    RadioButton rbNoEncryption;

    @BindView(R.id.edtNetwork)
    EditText edtNetwork;

    @BindView(R.id.txtPassword)
    TextView txtPassword;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.viewLeft)
    View viewLeft;
    @BindView(R.id.viewRight)
    View viewRight;

    private int check = 0;
    private String wifiDetail;

    private HomeActivity activity;

    private Class TAG = WifiFragment.class;

    public WifiFragment() {

    }

    public static WifiFragment getInstance() {
        return instance;
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        Utility.hideKeyboard(activity);
        activity.onBackPressed();
    }

    @OnClick(R.id.imgDone)
    public void onClickDone() {
        Utility.hideKeyboard(activity);

        try {
            if (check == 0) {
                if (edtNetwork.getText().toString().trim().equals("")) {
                    Snackbar.make(edtNetwork, getResources().getString(R.string.msgQrCodeWifiName), Snackbar.LENGTH_LONG).show();
                    edtNetwork.requestFocus();
                } else if (edtPassword.getText().toString().trim().equals("")) {
                    Snackbar.make(edtNetwork, getResources().getString(R.string.msgQrCodeWifiPass), Snackbar.LENGTH_LONG).show();
                    edtPassword.requestFocus();
                } else {
                    wifiDetail = "WIFI:T:WPA/WPA2;" + "S:" + edtNetwork.getText().toString() + ";" + "P:" + edtPassword.getText().toString() + ";";
                    setWifiDetails();
                }
            } else if (check == 1) {
                if (edtNetwork.getText().toString().trim().equals("")) {
                    Snackbar.make(edtNetwork, getResources().getString(R.string.msgQrCodeWifiName), Snackbar.LENGTH_LONG).show();
                    edtNetwork.requestFocus();
                } else if (edtPassword.getText().toString().trim().equals("")) {
                    Snackbar.make(edtNetwork, getResources().getString(R.string.msgQrCodeWifiPass), Snackbar.LENGTH_LONG).show();
                    edtPassword.requestFocus();
                } else {
                    wifiDetail = "WIFI:T:WEP;" + "S:" + edtNetwork.getText().toString() + ";" + "P:" + edtPassword.getText().toString() + ";";
                    setWifiDetails();
                }

            } else if (check == 2) {
                if (edtNetwork.getText().toString().trim().equals("")) {
                    Snackbar.make(edtNetwork, getResources().getString(R.string.msgQrCodeWifiName), Snackbar.LENGTH_LONG).show();
                    edtNetwork.requestFocus();
                } else {
                    wifiDetail = "WIFI:T:nopass;" + " S:" + edtNetwork.getText().toString() + ";" + "P:" + "" + ";";
                    setWifiDetails();
                }
            } else {

            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
        }
    }

    public void setWifiDetails() {
        Utility.setQrCodeData(wifiDetail, "WIFI", "QR_CODE");

        Fragment fragment = CreateQRCodeResultFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString("QrCodeData", wifiDetail);
        bundle.putString("QrCodeType", "WIFI");
        bundle.putString("QrCodeFormat", "QR_CODE");

        Utility.setBundleFragment(fragment, bundle);

        activity.pushFragment(fragment);
    }

    @OnClick(R.id.rbWpaWpa2)
    public void onClickWPAWPA2() {
        edtNetwork.setVisibility(View.VISIBLE);
        edtPassword.setVisibility(View.VISIBLE);
        txtPassword.setVisibility(View.VISIBLE);

        viewLeft.setVisibility(View.GONE);
        viewRight.setVisibility(View.VISIBLE);

        rbWpaWpa2.setBackgroundColor(Utility.fetchPrimaryColor(activity));
        rbWep.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);
        rbNoEncryption.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);

        imgDoneEnableAndDisable();

        check = 0;
    }

    @OnClick(R.id.rbWep)
    public void onClickWep() {
        edtNetwork.setVisibility(View.VISIBLE);
        edtPassword.setVisibility(View.VISIBLE);
        txtPassword.setVisibility(View.VISIBLE);

        viewLeft.setVisibility(View.GONE);
        viewRight.setVisibility(View.GONE);

        rbWep.setBackgroundColor(Utility.fetchPrimaryColor(activity));
        rbWpaWpa2.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);
        rbNoEncryption.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);

        imgDoneEnableAndDisable();

        check = 1;
    }

    @OnClick(R.id.rbNoEncryption)
    public void onClickNoEncryption() {
        edtNetwork.setVisibility(View.VISIBLE);
        edtPassword.setVisibility(View.GONE);
        txtPassword.setVisibility(View.GONE);

        viewLeft.setVisibility(View.VISIBLE);
        viewRight.setVisibility(View.GONE);

        rbNoEncryption.setBackgroundColor(Utility.fetchPrimaryColor(activity));
        rbWep.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);
        rbWpaWpa2.setBackground(/*activity.getResources().getColor(R.color.primaryWhite)*/null);

        imgDoneEnableAndDisable();

        check = 2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameWifi));

        Utility.keyboardVisibility(activity);

        /*Configuration config = activity.getResources().getConfiguration();

        if(config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
           rbWpaWpa2.setBackground(activity.getResources().getDrawable(R.drawable.control_switch_background_selector_right));
           rbNoEncryption.setBackground(activity.getResources().getDrawable(R.drawable.control_switch_background_selector_left));
        }else {
            rbWpaWpa2.setBackground(activity.getResources().getDrawable(R.drawable.control_switch_background_selector_left));
            rbNoEncryption.setBackground(activity.getResources().getDrawable(R.drawable.control_switch_background_selector_right));
        }*/

        rgToggle.getBackground().setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);
        rbWpaWpa2.getBackground().setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);
        rbWep.getBackground().setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);
        rbNoEncryption.getBackground().setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        imgDoneEnableAndDisable();
    }

    private void imgDoneEnableAndDisable() {
        edtNetwork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtNetwork.getText().toString().length() > 0) {
                    imgDone.setAlpha(1f);
                    imgDone.setEnabled(true);
                } else {
                    imgDone.setAlpha(0.5f);
                    imgDone.setEnabled(false);
                }
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtPassword.getText().toString().length() > 0) {
                    imgDone.setAlpha(1f);
                    imgDone.setEnabled(true);
                } else {
                    imgDone.setAlpha(0.5f);
                    imgDone.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
