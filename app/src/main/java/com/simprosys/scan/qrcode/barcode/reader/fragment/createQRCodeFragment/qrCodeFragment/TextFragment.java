package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
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
public class TextFragment extends Fragment {
    private static TextFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtMessage)
    EditText edtMessage;

    private HomeActivity activity;

    private Class TAG = TextFragment.class;

    String type;

    public TextFragment() {

    }

    public static TextFragment getInstance() {
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
            if (!edtMessage.getText().toString().trim().equals("")) {
                String textDetail = edtMessage.getText().toString();

                if(textDetail.contains("http://") || textDetail.contains("https://") || Patterns.WEB_URL.matcher(textDetail).matches()){
                    type = "URI";
                }else {
                    type = "TEXT";
                }

                Utility.setQrCodeData(textDetail, type, "QR_CODE");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("QrCodeData", textDetail);
                bundle.putString("QrCodeType", type);
                bundle.putString("QrCodeFormat", "QR_CODE");

                Utility.setBundleFragment(fragment, bundle);

                activity.pushFragment(fragment);

            } else {
                Snackbar.make(edtMessage, getResources().getString(R.string.msgQrCodeText), Snackbar.LENGTH_LONG).show();
                edtMessage.requestFocus();
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
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameText));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        Utility.imgDoneEnableAndDisable(edtMessage, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
