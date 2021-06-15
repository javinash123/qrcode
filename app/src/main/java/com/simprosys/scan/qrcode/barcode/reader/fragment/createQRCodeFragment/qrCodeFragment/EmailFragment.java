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
public class EmailFragment extends Fragment {
    private static EmailFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtEmail)
    EditText edtEmail;

    @BindView(R.id.edtSubject)
    EditText edtSubject;

    @BindView(R.id.edtBody)
    EditText edtBody;

    private HomeActivity activity;

    private Class TAG = EmailFragment.class;

    public EmailFragment() {

    }

    public static EmailFragment getInstance() {
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

            if (edtEmail.getText().toString().trim().equals("")) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeValidEmail), Snackbar.LENGTH_LONG).show();
                edtEmail.requestFocus();
            } else if (!edtEmail.getText().toString().trim().matches(emailPattern)) {
                Snackbar.make(edtEmail, getResources().getString(R.string.msgQrCodeValidEmailId), Snackbar.LENGTH_LONG).show();
                edtEmail.requestFocus();
            } else if (edtSubject.getText().toString().trim().equals("")) {
                Snackbar.make(edtSubject, getResources().getString(R.string.msgQrCodeValidEmailSubject), Snackbar.LENGTH_LONG).show();
                edtSubject.requestFocus();
            } else if (edtBody.getText().toString().trim().equals("")) {
                Snackbar.make(edtBody, getResources().getString(R.string.msgQrCodeValidEmailBody), Snackbar.LENGTH_LONG).show();
                edtBody.requestFocus();
            } else {
                String emailDetail = "smtp:" + edtEmail.getText().toString() + ":" + edtSubject.getText().toString() + ":"
                        + edtBody.getText().toString();

                Utility.setQrCodeData(emailDetail, "EMAIL_ADDRESS", "QR_CODE");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("QrCodeData", emailDetail);
                bundle.putString("QrCodeType", "EMAIL_ADDRESS");
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_email, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameEmail));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtEmail, imgDone);
        Utility.imgDoneEnableAndDisable(edtSubject, imgDone);
        Utility.imgDoneEnableAndDisable(edtBody, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
