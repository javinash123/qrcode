package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Code39Fragment extends BaseFragment {
    private static Code39Fragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtBarCode)
    EditText edtBarCode;

    private HomeActivity activity;

    private Class TAG = Code39Fragment.class;

    public Code39Fragment() {

    }

    public static Code39Fragment getInstance() {
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
                String code39Detail = edtBarCode.getText().toString();

                Utility.setQrCodeData(code39Detail, "TEXT", "CODE_39");

                Fragment fragment = CreateQRCodeResultFragment.newInstance();

                Bundle bundle = new Bundle();
                bundle.putString("BarCodeData", code39Detail);
                bundle.putString("BarCodeType", "TEXT");
                bundle.putString("BarCodeFormat", "CODE_39");

                Utility.setBundleFragment(fragment, bundle);


                activity.pushFragment(fragment);

            } else {
                Utility.errorMessageDialogShow(activity, getResources().getString(R.string.msgBarCode));
                edtBarCode.requestFocus();
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
        return inflater.inflate(R.layout.fragment_code39, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameCode39));

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start,
                                       int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) &&
                            !Character.toString(source.charAt(i)).equals("-") &&
                            !Character.toString(source.charAt(i)).equals(".") &&
                            !Character.toString(source.charAt(i)).equals(" ") &&
                            !Character.toString(source.charAt(i)).equals("$") &&
                            !Character.toString(source.charAt(i)).equals("/") &&
                            !Character.toString(source.charAt(i)).equals("+") &&
                            !Character.toString(source.charAt(i)).equals("+") &&
                            !Character.toString(source.charAt(i)).equals("%")) {
                        return "";
                    }
                }

                return null;
            }
        };

        edtBarCode.setFilters(new InputFilter[]{filter, new InputFilter.AllCaps()});

        edtBarCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        edtBarCode.setHint("Ex. (BARCODEKIT)");

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
