package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.InputType;
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
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.Code128Barcode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Code128Fragment extends Fragment {
    private static Code128Fragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtBarCode)
    EditText edtBarCode;

    private HomeActivity activity;

    private Class TAG = Code128Fragment.class;

    public Code128Fragment() {

    }

    public static Code128Fragment getInstance() {
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
                String code128Detail = edtBarCode.getText().toString();

                Code128Barcode code128Barcode = new Code128Barcode(code128Detail, activity);

                code128Barcode.getErrors();
                code128Barcode.getRawData();
                code128Barcode.getEncodedValue();

                Utility.setQrCodeData(code128Detail, "TEXT", "CODE_128");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("BarCodeData", code128Detail);
                bundle.putString("BarCodeType", "TEXT");
                bundle.putString("BarCodeFormat", "CODE_128");

                Utility.setBundleFragment(fragment, bundle);

                activity.pushFragment(fragment);

            } else {
                Utility.errorMessageDialogShow(activity, getResources().getString(R.string.msgBarCode));
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code128, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameCode128));

        edtBarCode.setInputType(InputType.TYPE_CLASS_TEXT);

        edtBarCode.setHint("Ex. (BaRcOdEkIt!)");

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
