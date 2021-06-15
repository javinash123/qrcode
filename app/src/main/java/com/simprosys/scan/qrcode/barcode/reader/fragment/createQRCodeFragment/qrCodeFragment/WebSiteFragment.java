package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
public class WebSiteFragment extends Fragment {
    private static WebSiteFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.txtQrCode)
    TextView txtQrCode;

    /*@BindView(R.id.edtUrl)
    EditText edtUrl;*/

    @BindView(R.id.edtQrCode)
    EditText edtQrCode;

    private HomeActivity activity;

    private Class TAG = WebSiteFragment.class;

    public WebSiteFragment() {

    }

    public static WebSiteFragment getInstance() {
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
            if (edtQrCode.getText().toString().trim().equals("")) {
                Snackbar.make(edtQrCode, getResources().getString(R.string.msgQrCodeWebSite), Snackbar.LENGTH_LONG).show();
                edtQrCode.requestFocus();
            } else if (!Patterns.WEB_URL.matcher(edtQrCode.getText().toString().trim()).matches()) {
                Snackbar.make(edtQrCode, getResources().getString(R.string.msgQrCodeValidWebSite), Snackbar.LENGTH_LONG).show();
                edtQrCode.requestFocus();
            } else {
                String urlDetail = edtQrCode.getText().toString();

                Utility.setQrCodeData(urlDetail, "URL", "QR_CODE");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("QrCodeData", urlDetail);
                bundle.putString("QrCodeType", "URL");
                bundle.putString("QrCodeFormat", "QR_CODE");

                Utility.setBundleFragment(fragment, bundle);

                activity.pushFragment(fragment);

            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_site, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameWebSite));

        txtQrCode.setText(getResources().getString(R.string.txtQrCodeWebSiteName));

        edtQrCode.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);

        edtQrCode.setHint(getResources().getString(R.string.txtHintWebSite));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtQrCode, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
