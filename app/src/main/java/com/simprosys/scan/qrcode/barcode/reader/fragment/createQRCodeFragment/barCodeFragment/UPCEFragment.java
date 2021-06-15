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
import com.simprosys.scan.qrcode.barcode.reader.fragment.BaseFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.UPCEBarcode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UPCEFragment extends BaseFragment {
    private static UPCEFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtBarCode)
    EditText edtBarCode;

    private HomeActivity activity;

    private Class TAG = UPCEFragment.class;

    public UPCEFragment() {

    }

    public static UPCEFragment getInstance() {
        return instance;
    }

    @OnClick(R.id.imgBack)public void onClickBack() {
        Utility.hideKeyboard(activity);
        activity.onBackPressed();
    }


    @OnClick(R.id.imgDone)
    public void onClickDone() {
        Utility.hideKeyboard(activity);

        try {
            if (!edtBarCode.getText().toString().trim().equals("")) {
                String upceDetail = edtBarCode.getText().toString();

                if (upceDetail.length() != 8) {
                    Snackbar.make(edtBarCode, getResources().getString(R.string.msgBarCodeUpce), Snackbar.LENGTH_LONG).show();
                }else {

                    UPCEBarcode upceBarcode = new UPCEBarcode(upceDetail, activity);

                    upceBarcode.getErrors();

                    upceBarcode.getRawData();

                    upceBarcode.getEncodedValue();

                    Utility.setQrCodeData(upceDetail, "PRODUCT", "UPC_E");

                    Fragment fragment = CreateQRCodeResultFragment.newInstance();

                    Bundle bundle = new Bundle();
                    bundle.putString("BarCodeData", upceDetail);
                    bundle.putString("BarCodeType", "PRODUCT");
                    bundle.putString("BarCodeFormat", "UPC_E");

                    Utility.setBundleFragment(fragment, bundle);

                    activity.pushFragment(fragment);

                /*if (upceDetail.length() != 8) {
                        Snackbar.make(edtBarCode, getResources().getString(R.string.upce_invalid_msg), Snackbar.LENGTH_LONG).show();
                        throw new IllegalArgumentException(
                                "Requested contents should be 8 digits long, but got " + upceDetail.length());
                    }

                    try {
                        if (!Utility.barCodeChecksum(upceDetail)) {
                            Snackbar.make(edtBarCode, getResources().getString(R.string.upce_check_sum_msg), Snackbar.LENGTH_LONG).show();
                            throw new IllegalArgumentException("Contents do not pass checksum " + upceDetail);
                        }
                    } catch (FormatException ignored) {
                        throw new IllegalArgumentException("Illegal contents " + ignored.getMessage());
                    }

                    Utility.setQrCodeData(upceDetail, "PRODUCT", "UPC_E");

                    Fragment fragment = CreateQRCodeResultFragment.newInstance();

                    Bundle bundle = new Bundle();
                    bundle.putString("BarCodeData", upceDetail);
                    bundle.putString("BarCodeType", "PRODUCT");
                    bundle.putString("BarCodeFormat", "UPC_E");

                    Utility.setBundleFragment(fragment, bundle);

                    FragmentUtils.replace(fragment, activity, "Fragment", "CreateQRCodeResult");*/
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
        return inflater.inflate(R.layout.fragment_upce, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameUPCE));

        edtBarCode.setKeyListener(DigitsKeyListener.getInstance(getResources().getString(R.string.inputBarCodeNum)));
        edtBarCode.setHint("Ex. (01234565)");

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
