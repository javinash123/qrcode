package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
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
public class LocationFragment extends Fragment {
    private static LocationFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.edtLatitude)
    EditText edtLatitude;

    @BindView(R.id.edtLongitude)
    EditText edtLongitude;

    private HomeActivity activity;

    private Class TAG = LocationFragment.class;

    public LocationFragment() {

    }

    public static LocationFragment getInstance() {
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
            if (edtLatitude.getText().toString().trim().equals("")) {
                Snackbar.make(edtLatitude, getResources().getString(R.string.msgQrCodeLatitude), Snackbar.LENGTH_LONG).show();
                edtLatitude.requestFocus();
            } else if (edtLongitude.getText().toString().trim().equals("")) {
                Snackbar.make(edtLatitude, getResources().getString(R.string.msgQrCodeLongitude), Snackbar.LENGTH_LONG).show();
                edtLongitude.requestFocus();
            } else {

                double lat = Double.parseDouble(edtLatitude.getText().toString());
                double lon = Double.parseDouble(edtLongitude.getText().toString());

                if(isValidLatLng(lat, lon)) {
              /*  if (edtLatitude.getText().toString().contains(".") && edtLongitude.getText().toString().contains(".")) {*/
                    String locationDetail = "geo:" + edtLatitude.getText().toString() + "," + edtLongitude.getText().toString();

                    Utility.setQrCodeData(locationDetail, "GEO", "QR_CODE");

                    Fragment fragment = CreateQRCodeResultFragment.newInstance();

                    Bundle bundle = new Bundle();
                    bundle.putString("QrCodeData", locationDetail);
                    bundle.putString("QrCodeType", "GEO");
                    bundle.putString("QrCodeFormat", "QR_CODE");

                    Utility.setBundleFragment(fragment, bundle);

                    activity.pushFragment(fragment);
                }else {
                    Snackbar.make(edtLongitude, getResources().getString(R.string.msgLocation), Snackbar.LENGTH_LONG).show();
                }
                /*} else {
                    Snackbar.make(edtLongitude, "Please provider contains(.)", Snackbar.LENGTH_LONG).show();
                }*/
            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
        }
    }

    public boolean isValidLatLng(double lat, double lng){
        if(lat < -90 || lat > 90) {
            return false;
        }
        else return !(lng < -180) && !(lng > 180);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameLocation));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start,
                                       int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {
                    if (!Character.isDigit(source.charAt(i)) &&
                            !Character.toString(source.charAt(i)).equals("-") &&
                            !Character.toString(source.charAt(i)).equals(".")) {
                        return "";
                    }
                }

                return null;
            }
        };

        /*edtLatitude.setFilters(new InputFilter[]{filter, new InputFilter.AllCaps()});
        edtLongitude.setFilters(new InputFilter[]{filter, new InputFilter.AllCaps()});*/

        /*edtLatitude.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edtLongitude.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);*/

        edtLatitude.setHint(getResources().getString(R.string.txtHintLatitude));
        edtLongitude.setHint(getResources().getString(R.string.txtHintLongitude));

        Utility.imgDoneEnableAndDisable(edtLatitude, imgDone);
        Utility.imgDoneEnableAndDisable(edtLongitude, imgDone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }
}
