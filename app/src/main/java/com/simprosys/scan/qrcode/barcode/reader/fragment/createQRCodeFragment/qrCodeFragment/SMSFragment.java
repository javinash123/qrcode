package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
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
public class SMSFragment extends Fragment {

    private static SMSFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtMessage)
    EditText edtMessage;

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    private HomeActivity activity;

    private Class TAG = SMSFragment.class;

    public SMSFragment() {

    }

    public static SMSFragment getInstance() {
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
            if (edtPhoneNumber.getText().toString().trim().equals("")) {
                Snackbar.make(edtPhoneNumber, getResources().getString(R.string.msgQrCodePhoneNum), Snackbar.LENGTH_LONG).show();
                edtPhoneNumber.requestFocus();
            } else if (!Utility.isValidPhone(edtPhoneNumber.getText().toString().trim())) {
                Snackbar.make(edtPhoneNumber, getResources().getString(R.string.msgQrCodeValidPhoneNum), Snackbar.LENGTH_LONG).show();
                edtPhoneNumber.requestFocus();
            } else if (edtMessage.getText().toString().trim().equals("")) {
                Snackbar.make(edtPhoneNumber, getResources().getString(R.string.msgQrCodeSms), Snackbar.LENGTH_LONG).show();
                edtMessage.requestFocus();
            } else {
                String smsDetail = "SMSTO:" + edtPhoneNumber.getText().toString() + ":" + edtMessage.getText().toString();

                Utility.setQrCodeData(smsDetail, "SMS", "QR_CODE");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("QrCodeData", smsDetail);
                bundle.putString("QrCodeType", "SMS");
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
        return inflater.inflate(R.layout.fragment_sms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameSms));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtPhoneNumber, imgDone);
        Utility.imgDoneEnableAndDisable(edtMessage, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
