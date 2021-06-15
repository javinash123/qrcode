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

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.UPCABarcode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UPCAFragment extends Fragment {
    private static UPCAFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtBarCode)
    EditText edtBarCode;

    private HomeActivity activity;

    private Class TAG = UPCAFragment.class;

    public UPCAFragment() {

    }

    public static UPCAFragment getInstance() {
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
                String upcaDetail = edtBarCode.getText().toString();

                if (upcaDetail.length() != 12) {
                    //Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCodeUpca), Snackbar.LENGTH_LONG).show();
                    throw new IllegalArgumentException(getResources().getString(R.string.msgBarCodeUpca));
                }

                UPCABarcode upcaBarcode = new UPCABarcode(upcaDetail, activity);

                upcaBarcode.getErrors();

                upcaBarcode.getRawData();

                upcaBarcode.getEncodedValue();

                if (upcaBarcode.getRawData().equals(upcaDetail)) {
                    Utility.setQrCodeData(upcaDetail, "PRODUCT", "UPC_A");

                    Fragment fragment = CreateQRCodeResultFragment.newInstance();

                    Bundle bundle = new Bundle();
                    bundle.putString("BarCodeData", upcaDetail);
                    bundle.putString("BarCodeType", "PRODUCT");
                    bundle.putString("BarCodeFormat", "UPC_A");

                    Utility.setBundleFragment(fragment, bundle);

                    activity.pushFragment(fragment);

                } else {
                    Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCodeValid), Snackbar.LENGTH_LONG).show();
                }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameUPCA));

        edtBarCode.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputBarCodeNum)));
        edtBarCode.setHint("Ex. (018200001680)");

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
