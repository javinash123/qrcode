package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.fragment.CreateQRCodeResultFragment;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.view.SwitchButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.getDateGMT;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    public static String startDate;
    public static String endDate;
    public static int dateAndTimeType = 0;
    public static int dateType = 0;
    private static EventFragment instance = null;

    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgDone)
    ImageView imgDone;

    @BindView(R.id.switchButton)
    SwitchButton switchButton;

    @BindView(R.id.edtTitle)
    EditText edtTitle;

    @BindView(R.id.edtLocation)
    EditText edtLocation;

    @BindView(R.id.leyDate)
    LinearLayout leyDate;

    @BindView(R.id.leyDateAndTime)
    LinearLayout leyDateAndTime;

    @BindView(R.id.edtBeginDate)
    EditText edtBeginDate;

    @BindView(R.id.edtEndDate)
    EditText edtEndDate;

    @BindView(R.id.edtBeginDateAndTime)
    EditText edtBeginDateAndTime;

    @BindView(R.id.edtEndDateAndTime)
    EditText edtEndDateAndTime;

    private int check = 0;
    private SwitchDateTimeDialogFragment dateTimeFragment;
    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat mFormat;
    private DateFormat dateFormat;
    private Date bdate, bDateAndTime, currentDate;
    private String beginDateAndTime;

    private HomeActivity activity;

    private Class TAG = VCardFragment.class;

    public EventFragment() {
    }

    public static EventFragment getInstance() {
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
            if(edtTitle.getText().toString().trim().equals("")){
                Snackbar.make(edtTitle, getResources().getString(R.string.msgQrCodeEventTitle), Snackbar.LENGTH_LONG).show();
                edtTitle.requestFocus();
            }else if(edtLocation.getText().toString().trim().equals("")){
                Snackbar.make(edtLocation, getResources().getString(R.string.msgQrCodeEventLocation), Snackbar.LENGTH_LONG).show();
                edtLocation.requestFocus();
            }else {
                if (check == 1) {
                    Log.e("TAG_", "check point : " + check);
                    if (edtBeginDate.getText().toString().trim().equals("")) {
                        Snackbar.make(edtBeginDate, getResources().getString(R.string.msgQrCodeEventBDateAndTime), Snackbar.LENGTH_LONG).show();
                        edtBeginDate.requestFocus();
                    } else if (edtEndDate.getText().toString().trim().equals("")) {
                        Snackbar.make(edtEndDate, getResources().getString(R.string.msgQrCodeEventEDateAndTime), Snackbar.LENGTH_LONG).show();
                        edtEndDate.requestFocus();
                    } else {
                        //setEventDetails();
                        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                        Date date1 = dateFormat.parse(edtBeginDate.getText().toString());
                        Date date2 = dateFormat.parse(edtEndDate.getText().toString());

                        if(date1.getTime() <  date2.getTime()){
                            setEventDetails();
                            // Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(edtEndDate, activity.getResources().getString(R.string.msgQrCodeEventDate), Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(activity, "no", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else /*if (check == 0)*/ {
                    Log.e("TAG_", "check point : " + check);
                    if (edtBeginDateAndTime.getText().toString().trim().equals("")) {
                        Snackbar.make(edtBeginDateAndTime, getResources().getString(R.string.msgQrCodeEventBDateAndTime), Snackbar.LENGTH_LONG).show();
                        edtBeginDateAndTime.requestFocus();
                    } else if (edtEndDateAndTime.getText().toString().trim().equals("")) {
                        Snackbar.make(edtEndDateAndTime, getResources().getString(R.string.msgQrCodeEventEDateAndTime), Snackbar.LENGTH_LONG).show();
                        edtEndDateAndTime.requestFocus();
                    } else {
                        //setEventDetails();
                        dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                        Date date1 = dateFormat.parse(edtBeginDateAndTime.getText().toString());
                        Date date2 = dateFormat.parse(edtEndDateAndTime.getText().toString());

                        if(date1.getTime() <  date2.getTime()){
                            setEventDetails();
                            // Toast.makeText(activity, "yes", Toast.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(edtEndDateAndTime, activity.getResources().getString(R.string.msgQrCodeEventDate), Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(activity, "no", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage() + " ");
        }
    }

    public void setEventDetails() {
        String eventDetail = "BEGIN:VEVENT"
                + "\nSUMMARY:" + edtTitle.getText().toString()
                + "\nLOCATION:" + edtLocation.getText().toString()
                + "\nDTSTART:" + startDate
                + "\nDTEND:" + endDate
                + "\nEND:VEVENT";

        Utility.setQrCodeData(eventDetail, "CALENDAR", "QR_CODE");

        Fragment fragment = CreateQRCodeResultFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putString("QrCodeData", eventDetail);
        bundle.putString("QrCodeType", "CALENDAR");
        bundle.putString("QrCodeFormat", "QR_CODE");

        Utility.setBundleFragment(fragment, bundle);

        activity.pushFragment(fragment);
    }

    @OnClick(R.id.edtBeginDate)
    public void onClickBeginDate() {
        if(!Utility.stopClick())
            return;

        dateType = 0;

        Calendar calendar = Calendar.getInstance();

        setupDatePickerDialog(calendar);

        datePickerDialog.setMinDate(calendar);
        datePickerDialog.show(activity.getFragmentManager(), "DpdBeginDate");

    }

    @OnClick(R.id.edtEndDate)
    public void onClickEndDate() {
        if(!Utility.stopClick())
            return;
        dateType = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bdate);

        setupDatePickerDialog(calendar);

        datePickerDialog.setMinDate(calendar);
        datePickerDialog.show(activity.getFragmentManager(), "DpdEndDate");
    }

    private void setupDatePickerDialog(Calendar calendar) {
        datePickerDialog = DatePickerDialog.newInstance(
                EventFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.edtBeginDateAndTime)
    public void onClickBeginDateAndTime() {
        if(!Utility.stopClick())
            return;
        dateAndTimeType = 0;

        if (!edtBeginDateAndTime.getText().toString().equals("")) {
            beginDateAndTime = edtBeginDateAndTime.getText().toString();

            mFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

            dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

            try {
                bDateAndTime = dateFormat.parse(beginDateAndTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (bDateAndTime != null) {
                Calendar calendar = Calendar.getInstance();

                if (mFormat.format(bDateAndTime).equals(mFormat.format(Calendar.getInstance().getTime()))) {
                    calendar.setTime(bDateAndTime);

                    setupSwitchDateTimeDialogFragment();

                    dateTimeFragment.startAtCalendarView();
                    dateTimeFragment.setMinimumDateTime(calendar.getTime());
                    dateTimeFragment.setDefaultDateTime(calendar.getTime());
                } else {
                    calendar.setTime(bDateAndTime);

                    setupSwitchDateTimeDialogFragment();

                    dateTimeFragment.startAtCalendarView();
                    dateTimeFragment.setMinimumDateTime(Calendar.getInstance().getTime());
                    dateTimeFragment.setDefaultDateTime(calendar.getTime());
                }
            }
        } else {
            setupSwitchDateTimeDialogFragment();

            dateTimeFragment.startAtCalendarView();
            dateTimeFragment.setMinimumDateTime(Calendar.getInstance().getTime());
            dateTimeFragment.setDefaultDateTime(Calendar.getInstance().getTime());
        }
    }

    @OnClick(R.id.edtEndDateAndTime)
    public void onClickEndDateAndTime() {
        if(!Utility.stopClick())
            return;
        dateAndTimeType = 1;

        setupSwitchDateTimeDialogFragment();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bdate);

        dateTimeFragment.startAtCalendarView();
        dateTimeFragment.setMinimumDateTime(calendar.getTime());
        dateTimeFragment.setDefaultDateTime(calendar.getTime());
    }

    private void setupSwitchDateTimeDialogFragment() {
        dateTimeFragment = (SwitchDateTimeDialogFragment) activity.getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);

        if (dateTimeFragment == null) {
            dateTimeFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(android.R.string.ok),
                    getString(android.R.string.cancel)
            );
        }

        dateTimeFragment.set24HoursMode(false);
        dateTimeFragment.setHighlightAMPMSelection(false);

        try {
            dateTimeFragment.setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Utility.logE(TAG, e.getMessage());
        }

        dateTimeFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonWithNeutralClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                mFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                if (dateAndTimeType == 0) {
                    if (currentDate.before(date)) {
                        edtBeginDateAndTime.setText(mFormat.format(date));
                        startDate = getDateGMT(date.getTime());
                    } else {
                        Toast.makeText(activity, activity.getResources().getString(R.string.msgDateAndTime), Toast.LENGTH_SHORT).show();
                    }
                    bdate = date;
                } else {
                    if (bDateAndTime.before(date)) {
                        edtEndDateAndTime.setText(mFormat.format(date));
                        endDate = getDateGMT(date.getTime());
                    } else {
                        Toast.makeText(activity, activity.getResources().getString(R.string.msgDateAndTime), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }

            @Override
            public void onNeutralButtonClick(Date date) {

            }
        });

        dateTimeFragment.show(activity.getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(getResources().getString(R.string.txtAppNameEvent));

        Utility.keyboardVisibility(activity);

        imgDone.setAlpha(0.3f);
        imgDone.setEnabled(false);

        switchButton.setChecked(false);
        switchButton.isChecked();
        switchButton.toggle();
        switchButton.toggle(false);
        switchButton.setShadowEffect(true);
        switchButton.setEnabled(true);
        switchButton.setEnableEffect(true);

        Utility.imgDoneEnableAndDisable(edtTitle, imgDone);
        Utility.imgDoneEnableAndDisable(edtLocation, imgDone);

        edtBeginDateAndTime.setText(Utility.getDateAndTime());
        edtEndDateAndTime.setText(Utility.getDateAndTime());

        Calendar calendar = Calendar.getInstance();

        startDate = getDateGMT(calendar.getTimeInMillis());
        endDate = getDateGMT(calendar.getTimeInMillis());

        currentDate = calendar.getTime();
        bdate = calendar.getTime();
        bDateAndTime = calendar.getTime();

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    check = 1;

                    currentDate = calendar.getTime();
                    bdate = calendar.getTime();

                    leyDate.setVisibility(View.VISIBLE);
                    leyDateAndTime.setVisibility(View.GONE);
                    edtBeginDate.setText("");
                    edtEndDate.setText("");
                } else {
                    check = 0;

                    currentDate = calendar.getTime();
                    bdate = calendar.getTime();

                    leyDate.setVisibility(View.GONE);
                    leyDateAndTime.setVisibility(View.VISIBLE);
                    edtBeginDateAndTime.setText("");
                    edtEndDateAndTime.setText("");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.mUnregistrar.unregister();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, (monthOfYear), dayOfMonth);

        if (dateType == 0) {
            edtBeginDate.setText(Utility.getDate(calendar.getTimeInMillis()));
            startDate = getDateGMT(calendar.getTimeInMillis());
            bdate = calendar.getTime();
        } else {
            edtEndDate.setText(Utility.getDate(calendar.getTimeInMillis()));
            endDate = getDateGMT(calendar.getTimeInMillis());
        }
    }
}
