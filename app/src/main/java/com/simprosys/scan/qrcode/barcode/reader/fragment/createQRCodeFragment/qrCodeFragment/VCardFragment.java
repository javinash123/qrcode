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
public class VCardFragment extends Fragment {
    private static VCardFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;

    @BindView(R.id.edtCity)
    EditText edtCity;

    @BindView(R.id.edtCompanyName)
    EditText edtCompanyName;

    private HomeActivity activity;

    private Class TAG = VCardFragment.class;

    public VCardFragment() {
    }

    public static VCardFragment getInstance() {
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
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (edtName.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeVcardName), Snackbar.LENGTH_LONG).show();
                edtName.requestFocus();
            } else if (edtEmail.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeValidEmail), Snackbar.LENGTH_LONG).show();
                edtEmail.requestFocus();
            } else if (!edtEmail.getText().toString().trim().matches(emailPattern)) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeValidEmailId), Snackbar.LENGTH_LONG).show();
                edtEmail.requestFocus();
            } else if (edtPhoneNumber.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodePhoneNum), Snackbar.LENGTH_LONG).show();
                edtPhoneNumber.requestFocus();
            } else if (!Utility.isValidPhone(edtPhoneNumber.getText().toString().trim())) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeValidPhoneNum), Snackbar.LENGTH_LONG).show();
                edtPhoneNumber.requestFocus();
            } else if (edtCity.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeVcardCity), Snackbar.LENGTH_LONG).show();
                edtCity.requestFocus();
            } else if (edtCompanyName.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeVcardCompanyName), Snackbar.LENGTH_LONG).show();
                edtCompanyName.requestFocus();
            } else {
                String vCarDetail = "BEGIN:VCARD:\nN:" + edtName.getText().toString() + "\nEMAIL;INTERNET:" + edtEmail.getText().toString()
                        + "\nTEL;CELL:" + edtPhoneNumber.getText().toString() + "\nADR:" + edtCity.getText().toString()
                        + "\nORG:" + edtCompanyName.getText().toString() + "\nEND:VCARD";

                Utility.setQrCodeData(vCarDetail, "ADDRESSBOOK", "QR_CODE");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("QrCodeData", vCarDetail);
                bundle.putString("QrCodeType", "ADDRESSBOOK");
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
        return inflater.inflate(R.layout.fragment_vcard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameVCard));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtName, imgDone);
        Utility.imgDoneEnableAndDisable(edtEmail, imgDone);
        Utility.imgDoneEnableAndDisable(edtPhoneNumber, imgDone);
        Utility.imgDoneEnableAndDisable(edtCity, imgDone);
        Utility.imgDoneEnableAndDisable(edtCompanyName, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
