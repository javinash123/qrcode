package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.FormatException;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.fragment.BaseFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EAN8Fragment extends BaseFragment {
    private static EAN8Fragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtBarCode)
    EditText edtBarCode;

    private HomeActivity activity;

    private Class TAG = EAN8Fragment.class;

    public EAN8Fragment() {

    }

    public static EAN8Fragment getInstance() {
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
            if (!edtBarCode.getText().toString().trim().equals("")) {
                String ean8Detail = edtBarCode.getText().toString();

                if (ean8Detail.length() != 8) {
                    //Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCodeEAN8), Snackbar.LENGTH_LONG).show();
                    throw new IllegalArgumentException(getResources().getString(R.string.msgBarCodeEAN8));
                }

                try {
                    if (!Utility.barCodeChecksum(ean8Detail)) {
                        //Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCodeValid), Snackbar.LENGTH_LONG).show();
                        throw new IllegalArgumentException(getResources().getString(R.string.msgBarCodeValid));
                    }
                } catch (FormatException ignored) {
                    throw new IllegalArgumentException("Illegal contents " + ignored.getMessage());
                }

                Utility.setQrCodeData(ean8Detail, "PRODUCT", "EAN_8");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("BarCodeData", ean8Detail);
                bundle.putString("BarCodeType", "PRODUCT");
                bundle.putString("BarCodeFormat", "EAN_8");

                Utility.setBundleFragment(fragment, bundle);
                activity.pushFragment(fragment);

            } else {
                Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCode), Snackbar.LENGTH_LONG).show();
                edtBarCode.requestFocus();
            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
            Snackbar.make(edtBarCode, e.getMessage() + " ", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ean8, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameEAN8));

        edtBarCode.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputBarCodeNum)));
        edtBarCode.setHint("Ex. (96385074)");

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtBarCode, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
