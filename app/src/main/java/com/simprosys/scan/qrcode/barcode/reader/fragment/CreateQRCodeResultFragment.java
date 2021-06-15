package com.simprosys.scan.qrcode.barcode.reader.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.Telephony;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.google.zxing.client.result.TelParsedResult;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.google.zxing.client.result.WifiParsedResult;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.simprosys.scan.qrcode.barcode.reader.BuildConfig;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.BarcodeView;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.adapter.ImageAdapter;
import com.simprosys.scan.qrcode.barcode.reader.database.query.Query;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable;
import com.simprosys.scan.qrcode.barcode.reader.model.ImageItem;
import com.simprosys.scan.qrcode.barcode.reader.utility.CodeUtils;
import com.simprosys.scan.qrcode.barcode.reader.utility.CompressImageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Constant;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.qrCodeRead;
import static java.lang.String.format;

//import com.shockwave.pdfium.PdfiumCore;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateQRCodeResultFragment extends Fragment {
    private static final int REQ_CODE_STORAGE_PERM = 1;
    private static CreateQRCodeResultFragment instance = null;
    private static String SCANTYPE = "SCAN_TYPE";
    public Boolean hasStoragePermGranted = false;
    public boolean isResult = false;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.txtAppName)
    TextView txtAppName;
    @BindView(R.id.imgShare)
    ImageView imgShare;
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    @BindView(R.id.txtType)
    TextView txtType;
    @BindView(R.id.relQrCode)
    RelativeLayout relQrCode;
    @BindView(R.id.imgQrCodeBg)
    ImageView imgQrCodeBg;
    @BindView(R.id.imgQrCode)
    ImageView imgQrCode;
    /*@BindView(R.id.imgCodeLogo)
    CircleImageView imgCodeLogo;*/
    @BindView(R.id.frmQrCodeLogo)
    FrameLayout frmQrCodeLogo;
    @BindView(R.id.imgQrCodeLogo)
    ImageView imgQrCodeLogo;
    @BindView(R.id.relBarCode)
    RelativeLayout relBarCode;
    @BindView(R.id.imgBarCodeBg)
    ImageView imgBarCodeBg;
    @BindView(R.id.imgBarCode)
    ImageView imgBarCode;
    @BindView(R.id.recImgHorizontal)
    RecyclerView recImgHorizontal;
    @BindView(R.id.txtValue)
    TextView txtValue;
    @BindView(R.id.txtValueWifi1)
    TextView txtValueWifi1;
    @BindView(R.id.leyPassWord)
    LinearLayout leyPassWord;
    @BindView(R.id.txtValueWifi)
    TextView txtValueWifi;
    @BindView(R.id.txtValueWifi2)
    TextView txtValueWifi2;
    @BindView(R.id.txtValueWifi3)
    TextView txtValueWifi3;
    @BindView(R.id.leyBarCode)
    LinearLayout leyBarCode;
    @BindView(R.id.leyText)
    LinearLayout leyText;
    @BindView(R.id.leyCall)
    LinearLayout leyCall;
    @BindView(R.id.leySMS)
    LinearLayout leySMS;
    @BindView(R.id.leyEmail)
    LinearLayout leyEmail;
    @BindView(R.id.leyURL)
    LinearLayout leyURL;
    @BindView(R.id.leyDirection)
    LinearLayout leyDirection;
    @BindView(R.id.leyWIFI)
    LinearLayout leyWIFI;
    @BindView(R.id.leyContact)
    LinearLayout leyContact;
    @BindView(R.id.leyEvent)
    LinearLayout leyEvent;
    @BindView(R.id.leyFaceBook)
    LinearLayout leyFaceBook;
    @BindView(R.id.leyInstaGram)
    LinearLayout leyInstaGram;
    @BindView(R.id.leyWhatsApp)
    LinearLayout leyWhatsApp;
    @BindView(R.id.leyTwitter)
    LinearLayout leyTwitter;
    @BindView(R.id.leyLinkedIn)
    LinearLayout leyLinkedIn;
    @BindView(R.id.leySnapChat)
    LinearLayout leySnapChat;
    String scanType;
    String contents;
    int barCodeType;
    float barCodeHeight;
    float absoluteX;
    float absoluteY;
    float parcent;
    String charset = "UTF-8";
    String codeData;
    String codeType;
    String codeFormat;
    String convertFormat;
    BarcodeFormat barcodeNewFormat;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //removeProgress();
            Utility.removeDefaultProgress();
        }
    };
    private Bitmap bitmap = null;
    private Bitmap bitmap2 = null;
    private Bundle bundle;
    private Uri uri;
    private URL url;
    private HistoryTable historyTable;
    private EditText password;
    private ImageAdapter mAdapter;
    private ArrayList<ImageItem> mItemList = new ArrayList();
    private HomeActivity activity;
    private String shareDetail;
    private String detail;
    private String formats;
    private String title;
    private String wifiName;
    private String wifiPass;
    private String wifiNoEncryption;
    private String typeName;
    private Class TAG = CreateQRCodeResultFragment.class;

    public CreateQRCodeResultFragment() {
    }

    public static CreateQRCodeResultFragment getInstance() {
        return instance;
    }

    public static CreateQRCodeResultFragment newInstance() {
        try {
            Bundle args = new Bundle();
            CreateQRCodeResultFragment fragment = new CreateQRCodeResultFragment();
            fragment.setArguments(args);
            return fragment;
        } catch (Exception e) {
            return null;
        }
    }

    public static CreateQRCodeResultFragment newInstance(String scanType) {
        Bundle bundle = new Bundle();
        CreateQRCodeResultFragment fragment = new CreateQRCodeResultFragment();
        bundle.putString(SCANTYPE, scanType);
        fragment.setArguments(bundle);
        return fragment;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public static ParcelFileDescriptor openFile(File file) {
        ParcelFileDescriptor descriptor;
        try {
            descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return descriptor;
    }

    public static Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
        return resultBitmap;
    }

    @OnClick(R.id.imgBack)
    public void onClickBack() {
        Utility.hideKeyboard(activity);
        activity.onBackPressed();
    }

    @OnClick(R.id.imgShare)
    public void onClickShare() {
        if (!Utility.stopClick())
            return;
        checkPermissionStorage();

        if (hasStoragePermGranted) {
            @SuppressLint("SimpleDateFormat")
            String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());

            if (relQrCode.getVisibility() == View.VISIBLE) {
                View content = relQrCode;
                content.setDrawingCacheEnabled(true);
                bitmap2 = content.getDrawingCache();

                if (bitmap2 != null) {
                    shareBitmap(bitmap2, fileName, content);
                } else {
                    Toast.makeText(activity, "Something went wrong, Try again to generate",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                View content = relBarCode;
                content.setDrawingCacheEnabled(true);
                bitmap2 = content.getDrawingCache();

                if (bitmap2 != null) {
                    shareBitmap(bitmap2, fileName, content);
                } else {
                    Toast.makeText(activity, "Something went wrong, Try again to generate",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void checkPermissionStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                final String[] LOCATION_PERMS = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                hasStoragePermGranted = false;
                requestPermissions(LOCATION_PERMS, REQ_CODE_STORAGE_PERM);
            } else {
                hasStoragePermGranted = true;
            }
        } else {
            hasStoragePermGranted = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, perm));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_STORAGE_PERM:
                hasStoragePermGranted = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    @SuppressLint("SetWorldReadable")
    private void shareBitmap(Bitmap bitmap, String fileName, View view) {
        try {

            File file = Utility.getDirectoryFilePath(activity, "Shared", fileName);

            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            file.setReadable(true, false);

            Utility.addImageToGallery(file.getAbsolutePath(), activity);

            Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider",
                    Utility.getDirectoryFilePath(activity, "Shared", fileName));

            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.setType("image/png");

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            view.setDrawingCacheEnabled(false);
        }
    }

    @OnClick(R.id.leyBarCode)
    public void onClickBarCode() {
        if (!Utility.stopClick())
            return;
        try {
            uri = Uri.parse("http://www.google.com" + "/m/products?q=" + /*Utility.qrCodeResult*/ codeData);
            Utility.goToActionPlatform(activity, Intent.ACTION_VIEW, uri);
        } catch (Exception e) {
            e.printStackTrace();
            Utility.setToastMessage(activity, getResources().getString(R.string.msgNotOpenAction), 0);
        }
    }

    @OnClick(R.id.leyText)
    public void onClickText() {
        if (!Utility.stopClick())
            return;
        try {
            TextParsedResult textResult = (TextParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            uri = Uri.parse("http://www.google.com" + "/#q=" + textResult.getText());
            Utility.goToActionPlatform(activity, Intent.ACTION_VIEW, uri);
        } catch (Exception e) {
            e.printStackTrace();
            Utility.setToastMessage(activity, getResources().getString(R.string.msgNotOpenAction), 0);
        }
    }

    @OnClick(R.id.leyCall)
    public void onClickCall() {
        if (!Utility.stopClick())
            return;
        try {
            ParsedResult parsedResult = ResultParser.parseResult(Utility.qrCodeResult);
            Intent intent = new Intent(Intent.ACTION_DIAL);

            switch (parsedResult.getType().name()) {
                case "TEL":
                    TelParsedResult resultTel = (TelParsedResult) ResultParser.parseResult(Utility.qrCodeResult);
                    intent.setData(Uri.parse("tel:" + resultTel.getNumber()));
                    break;

                case "ADDRESSBOOK":
                    AddressBookParsedResult resultAddress = (AddressBookParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

                    if (resultAddress.getPhoneNumbers() != null)
                        intent.setData(Uri.parse("tel:" + resultAddress.getPhoneNumbers()[0]));
                    break;
            }

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leySMS)
    public void onClickSMS() {
        if (!Utility.stopClick())
            return;
        try {
            SMSParsedResult smsResult = (SMSParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getActivity());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                //intent.putExtra(Intent.EXTRA_TEXT, "text");

                if (defaultSmsPackageName != null) {
                    intent.setPackage(defaultSmsPackageName);
                }

                if (smsResult.getNumbers() != null)
                    intent.putExtra("address", Arrays.toString(smsResult.getNumbers())
                            .replace("[", "").replace("]", ""));

                if (smsResult.getBody() != null)
                    intent.putExtra("sms_body", smsResult.getBody());
                startActivity(intent);
            } else {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("vnd.android-dir/mms-sms");

                if (smsResult.getNumbers() != null)
                    sendIntent.putExtra("address", Arrays.toString(smsResult.getNumbers())
                            .replace("[", "").replace("]", ""));

                if (smsResult.getBody() != null)
                    sendIntent.putExtra("sms_body", smsResult.getBody());

                startActivity(sendIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("LongLogTag")
    @OnClick(R.id.leyEmail)
    public void onClickEmail() {
        if (!Utility.stopClick())
            return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");

        EmailAddressParsedResult emailResult = (EmailAddressParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

        if (emailResult.getEmailAddress() != null)
            intent.putExtra(Intent.EXTRA_EMAIL, emailResult.getEmailAddress());

        if (emailResult.getSubject() != null)
            intent.putExtra(Intent.EXTRA_SUBJECT, emailResult.getSubject());

        if (emailResult.getBody() != null)
            intent.putExtra(Intent.EXTRA_TEXT, emailResult.getBody());

        try {
            startActivity(Intent.createChooser(intent, "Send email..."));

            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leyURL)
    public void onClickURL() {
        if (!Utility.stopClick())
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            URIParsedResult resultURI = (URIParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            intent.setData(Uri.parse(resultURI.getURI()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leyDirection)
    public void onClickDirection() {
        try {
            GeoParsedResult geoResult = (GeoParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", geoResult.getLatitude(), geoResult.getLongitude());
            String uri = "http://maps.google.com/maps?q=loc:" + geoResult.getLatitude() + "," + geoResult.getLongitude() + " (" + "" + ")";
            Log.e("uri map", uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(/*"http://maps.google.com"
                    + "/maps?f=d&daddr=" + geoResult.getLatitude() + ','
                    + geoResult.getLongitude()*/ uri));

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leyWIFI)
    public void onClickWIFI() {
        if (!Utility.stopClick())
            return;
        try {
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Code Copied", wifiPass);
            clipboard.setPrimaryClip(clip);

            if (wifiPass.equals("")) {
                Toast.makeText(getActivity(), activity.getResources().getString(R.string.txtNoPassword), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), activity.getResources().getString(R.string.txtCopyClipboard), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leyContact)
    public void onClickContact() {
        if (!Utility.stopClick())
            return;
        try {
            ParsedResult parsedResult = ResultParser.parseResult(Utility.qrCodeResult);

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

            switch (parsedResult.getType().name()) {

                case "EMAIL_ADDRESS":
                    EmailAddressParsedResult emailAddressResult = (EmailAddressParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

                    if (emailAddressResult.getEmailAddress() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailAddressResult.getEmailAddress());
                    break;

                case "TEL":
                    TelParsedResult telResult = (TelParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

                    if (telResult.getTitle() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, telResult.getTitle());
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, telResult.getNumber());
                    break;

                case "ADDRESSBOOK":
                    AddressBookParsedResult addressBookResult = (AddressBookParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

                    if (addressBookResult.getPhoneNumbers() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, Arrays.toString(addressBookResult.getPhoneNumbers())
                                .replace("[", "").replace("]", ""));

                    if (addressBookResult.getEmails() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Arrays.toString(addressBookResult.getEmails())
                                .replace("[", "").replace("]", ""));

                    if (addressBookResult.getNames() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, Arrays.toString(addressBookResult.getNames())
                                .replace("[", "").replace("]", ""));

                    if (addressBookResult.getInstantMessenger() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.IM_HANDLE, addressBookResult.getInstantMessenger());

                    if (addressBookResult.getNote() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.NOTES, addressBookResult.getNote());

                    if (addressBookResult.getOrg() != null)
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, addressBookResult.getOrg());
                    break;
            }

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.leyEvent)
    public void onClickEvent() {
        if (!Utility.stopClick())
            return;
        try {
            CalendarParsedResult calendarResult = (CalendarParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            addCalendarEvent(calendarResult.getSummary(), calendarResult.getStart(), false, calendarResult.getEnd(),
                    calendarResult.getLocation(), calendarResult.getDescription(), calendarResult.getAttendees());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.leyFaceBook, R.id.leyInstaGram, R.id.leyWhatsApp, R.id.leyTwitter, R.id.leyLinkedIn, R.id.leySnapChat})
    public void onClickAllUrl() {
        if (!Utility.stopClick())
            return;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            URIParsedResult resultURI = (URIParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            intent.setData(Uri.parse(resultURI.getURI()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), getResources().getString(R.string.msgNotOpenAction), Toast.LENGTH_SHORT).show();
        }
    }

    private void addCalendarEvent(String summary, Date start, boolean allDay, Date end, String location, String description, String[] attendees) {

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.row_qr_and_bar_code_generate/event");

        long startMilliseconds = start.getTime();
        intent.putExtra("beginTime", startMilliseconds);

        if (allDay) {
            intent.putExtra("allDay", true);
        }

        long endMilliseconds;

        if (end == null) {
            if (allDay) {
                endMilliseconds = startMilliseconds + 24 * 60 * 60 * 1000;
            } else {
                endMilliseconds = startMilliseconds;
            }
        } else {
            endMilliseconds = end.getTime();
        }

        intent.putExtra("endTime", endMilliseconds);
        intent.putExtra("title", summary);
        intent.putExtra("eventLocation", location);
        intent.putExtra("description", description);

        if (attendees != null) {
            intent.putExtra(Intent.EXTRA_EMAIL, attendees);
        }

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            Log.w("Calendar", "No calendar app available that responds to " + Intent.ACTION_INSERT);
            intent.setAction(Intent.ACTION_EDIT);
            startActivity(intent);
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
        return inflater.inflate(R.layout.fragment_create_qrcode_result, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Constant.isPopFragment = 1;

        //Utility.ShowStatusBar(activity);

        setVisibilityGone();

        scanType = getArguments().getString(SCANTYPE);
        Log.e("scan result", "yes");
        if (scanType != null) {
            if (scanType.equals("ScanResultData")) {
                activity.bottomNavViewEx.setVisibility(View.GONE);
                setScanResultData();
            } else if (scanType.equals("ScanResultDataForHistory")) {
                activity.bottomNavViewEx.setVisibility(View.VISIBLE);
                setScanResultData();
            }
        } else {
            try {
                bundle = this.getArguments();


                if (bundle != null) {
                    String QrCodeFormat = bundle.getString("QrCodeFormat");
                    String barCodeFormat = bundle.getString("BarCodeFormat");

                    if (QrCodeFormat != null) {
                        switch (QrCodeFormat) {
                            case "QR_CODE":
                                codeData = bundle.getString("QrCodeData");
                                codeType = bundle.getString("QrCodeType");
                                codeFormat = bundle.getString("QrCodeFormat");

                                /*QrGenerateEncoder(codeData);

                                if (isResult) {
                                    ParsedResult parsedResult = ResultParser.parseResult(Utility.qrCodeResult);

                                    if (codeData != null && codeType != null && codeFormat != null) {
                                        encodeQRCodeContents(Utility.qrCodeResult.getText(), parsedResult.getType().name(),
                                                Utility.qrCodeResult.getBarcodeFormat().name(), Utility.qrCodeResult);
                                    } else {
                                        Utility.logE(TAG, "Qr code Data Null");
                                    }
                                } else {
                                    new MaterialDialog.Builder(activity)
                                            .title(R.string.appName)
                                            .content(R.string.msgNoReadCodeContent)
                                            .neutralText(R.string.msgOk)
                                            .itemsColor(Utility.fetchPrimaryColor(activity))
                                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    activity.onBackPressed();
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                    Utility.logE(TAG, "Can not read code");
                                }*/
                                new LoadQRBitmap().execute();
                                setImageThumb();
                                break;
                        }
                    } else if (barCodeFormat != null) {
                        switch (barCodeFormat) {
                            case "EAN_13":
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                barcodeNewFormat = BarcodeFormat.EAN_13;
                                //codeFormat = bundle.getString("BarCodeFormat");
                                codeFormat = "EAN-13";

                                //barCodeGenerateEncoder(codeData, codeFormat);

                                barCodeType = BarcodeEAN.EAN13;
                                barCodeHeight = 100;
                                absoluteX = 50;
                                absoluteY = 80;
                                parcent = 600;
                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                break;
                            case "EAN_8":
                                barcodeNewFormat = BarcodeFormat.EAN_8;
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                //codeFormat = bundle.getString("BarCodeFormat");
                                codeFormat = "EAN-8";

                                barCodeType = BarcodeEAN.EAN8;
                                barCodeHeight = 60;
                                absoluteX = 50;
                                absoluteY = 80;
                                parcent = 950;

                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                break;

                            case "UPC_E":
                                barcodeNewFormat = BarcodeFormat.UPC_E;
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                //codeFormat = bundle.getString("BarCodeFormat");

                                codeFormat = "UPC-E";

                             /*   barCodeType = BarcodeEAN.UPCA;
                                barCodeHeight = 100;
                                absoluteX = 50;
                                absoluteY = 80;
                                parcent = 600;*/

                                barCodeType = BarcodeEAN.UPCE;
                                barCodeHeight = 60;
                                absoluteX = 50;
                                absoluteY = 80;
                                parcent = 480;

                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                break;

                            case "CODE_39":
                                barcodeNewFormat = BarcodeFormat.CODE_39;
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                //codeFormat = bundle.getString("BarCodeFormat");

                                codeFormat = "CODE 39";

                                barCodeType = 4;
                                barCodeHeight = 60;
                                absoluteX = 50;
                                absoluteY = 310;

                                //parcent=codeData.length() * 46;

                                parcent = 600;

                                if (codeData.length() > 7) {
                                    for (int i = 8; i < codeData.length(); i++) {
                                        parcent = parcent - 45;
                                        if (parcent <= 0) {
                                            parcent = 0;
                                            break;
                                        }
                                    }
                                }

                                //parcent = 540;

                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                /*createBarCode(codeData);*/

                                break;
                            case "CODE_128":
                                barcodeNewFormat = BarcodeFormat.CODE_128;
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                //codeFormat = bundle.getString("BarCodeFormat");

                                codeFormat = "CODE 128";

                                barCodeType = Barcode128.CODE128;
                                barCodeHeight = 60;
                                absoluteX = 50;
                                absoluteY = 310;
                                parcent = 540;

                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                break;

                            case "UPC_A":
                                barcodeNewFormat = BarcodeFormat.UPC_A;
                                codeData = bundle.getString("BarCodeData");
                                codeType = bundle.getString("BarCodeType");
                                //codeFormat = bundle.getString("BarCodeFormat");

                                codeFormat = "UPC-A";

                                barCodeType = BarcodeEAN.UPCA;
                                barCodeHeight = 100;
                                absoluteX = 50;
                                absoluteY = 80;
                                parcent = 600;

                                barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                        absoluteX, absoluteY, parcent);

                                break;
                            default:
                                //Utility.removeProgress();
                                new MaterialDialog.Builder(activity)
                                        .title(R.string.appName)
                                        .content(R.string.msgNoReadCodeContent)
                                        .neutralText(R.string.msgOk)
                                        .itemsColor(Utility.fetchPrimaryColor(activity))
                                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                activity.onBackPressed();
                                            }
                                        })
                                        .cancelable(false)
                                        .show();
                        }
                        //Log.e("code type", codeFormat);
                        if (codeData != null && codeType != null && codeFormat != null) {
                            encodeBarCodeContents(codeData, codeType, codeFormat);
                        } else {
                            //Utility.logE(TAG, "Barcode Data Null");
                            /*Utility.removeProgress();
                            new MaterialDialog.Builder(activity)
                                    .title(R.string.appName)
                                    .content(R.string.msgNoReadCodeContent)
                                    .neutralText(R.string.msgOk)
                                    .itemsColor(Utility.fetchPrimaryColor(activity))
                                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            activity.onBackPressed();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();*/
                        }
                    }
                }
            } catch (Exception e) {
                //Toast.makeText(activity, e.toString() + "", Toast.LENGTH_LONG).show();
                new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();
            }
        }

        //setImageThumb();

        mAdapter = new ImageAdapter(activity, mItemList);
        recImgHorizontal.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recImgHorizontal.setAdapter(mAdapter);

        mAdapter.setOnItemClick(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (relQrCode.getVisibility() == View.VISIBLE) {
                    if (position == 0) {
                        showImageDialog();
                    } else if (position == 1) {
                        imgQrCodeBg.setVisibility(View.GONE);
                        frmQrCodeLogo.setVisibility(View.GONE);
                        imgQrCodeLogo.setVisibility(View.GONE);
                        setQrCodeColor(codeData, BLACK, WHITE);

                    } else if (position == 2) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_1));
                        setQrCodeColor(codeData, BLACK, WHITE);

                    } else if (position == 3) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_2));
                        setQrCodeColor(codeData, Color.parseColor("#01579B"), Color.parseColor("#005fe8ff"));

                    } else if (position == 4) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_3));
                        setQrCodeColor(codeData, Color.parseColor("#3B220E"), Color.parseColor("#00f79e76"));

                    } else if (position == 5) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_4));
                        setQrCodeColor(codeData, Color.parseColor("#380A49"), Color.parseColor("#00e9b0f4"));

                    } else if (position == 6) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_5));
                        setQrCodeColor(codeData, Color.parseColor("#DE5C4B"), WHITE);

                    } else if (position == 7) {
                        imgQrCodeBg.setVisibility(View.VISIBLE);
                        imgQrCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.qr_frame_6));
                        setQrCodeColor(codeData, Color.parseColor("#E65100"), Color.parseColor("#00a5d6a7"));
                    }
                } else {
                    if (position == 0) {
                        imgBarCodeBg.setVisibility(View.GONE);
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    } else if (position == 1) {
                        imgBarCodeBg.setVisibility(View.VISIBLE);
                        imgBarCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.bar_code_frame_1));
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00fdd3a9"), Color.parseColor("#44038A"), barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    } else if (position == 2) {
                        imgBarCodeBg.setVisibility(View.VISIBLE);
                        imgBarCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.bar_code_frame_2));
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00E5206F"), Color.parseColor("#000000"), barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    } else if (position == 3) {
                        imgBarCodeBg.setVisibility(View.VISIBLE);
                        imgBarCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.bar_code_frame_3));
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.parseColor("#941C80"), barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    } else if (position == 4) {
                        imgBarCodeBg.setVisibility(View.VISIBLE);
                        imgBarCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.bar_code_frame_4));
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00C4FEE6"), Color.parseColor("#4416A4"), barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    } else if (position == 5) {
                        imgBarCodeBg.setVisibility(View.VISIBLE);
                        imgBarCodeBg.setImageDrawable(getResources().getDrawable(R.drawable.bar_code_frame_5));
                        barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00EDEDED"), Color.BLACK, barCodeHeight,
                                absoluteX, absoluteY, parcent);
                    }
                }
            }
        });
    }

    private void setScanResultData() {
        ParsedResult parsedResult = ResultParser.parseResult(Utility.qrCodeResult);

        encodeQRCodeContents(Utility.qrCodeResult.getText(), parsedResult.getType().name(), Utility.qrCodeResult.getBarcodeFormat().name(),
                Utility.qrCodeResult);

        String barCodeFormat = Utility.qrCodeResult.getBarcodeFormat().name();

        if (barCodeFormat != null) {

            codeData = Utility.qrCodeResult.getText();
            codeType = parsedResult.getType().name();
            codeFormat = Utility.qrCodeResult.getBarcodeFormat().name();

            switch (barCodeFormat) {
                case "QR_CODE":
                    QrGenerateEncoder(codeData);

                    if (isResult) {

                    } else {
                        new MaterialDialog.Builder(activity)
                                .title(R.string.appName)
                                .content(R.string.msgNoReadCodeContent)
                                .neutralText(R.string.msgOk)
                                .itemsColor(Utility.fetchPrimaryColor(activity))
                                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        activity.onBackPressed();
                                    }
                                })
                                .cancelable(false)
                                .show();
                        Utility.logE(TAG, "Can not read code");
                    }
                    setImageThumb();
                    break;

                case "EAN_13":
                    barcodeNewFormat = BarcodeFormat.EAN_13;
                    barCodeType = BarcodeEAN.EAN13;
                    barCodeHeight = 100;
                    absoluteX = 50;
                    absoluteY = 80;
                    parcent = 600;

                    codeFormat = "EAN-13";

                           /* barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00e5e5e5"), Color.BLACK, barCodeHeight,
                                    absoluteX, absoluteY, parcent);*/

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);

                    break;
                case "EAN_8":
                    //barCodeGenerateEncoder(codeData, codeFormat);
                    barcodeNewFormat = BarcodeFormat.EAN_8;
                    barCodeType = BarcodeEAN.EAN8;
                    barCodeHeight = 60;
                    absoluteX = 50;
                    absoluteY = 80;
                    parcent = 950;

                    codeFormat = "EAN-8";

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);

                    break;
                case "UPC_E":
                          /*barCodeType = BarcodeEAN.UPCE;
                            barCodeHeight = 58;
                            absoluteX = 50;
                            absoluteY = 80;
                            parcent = 990;*/
                    barcodeNewFormat = BarcodeFormat.UPC_E;

                    barCodeType = BarcodeEAN.UPCE;
                    barCodeHeight = 60;
                    absoluteX = 50;
                    absoluteY = 80;
                    parcent = 480;

                    codeFormat = "UPC-E";

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);

                    break;
                case "CODE_39":
                    barcodeNewFormat = BarcodeFormat.CODE_39;
                    barCodeType = 4;
                    barCodeHeight = 60;
                    absoluteX = 50;
                    absoluteY = 310;
                    parcent = 540;

                    codeFormat = "CODE 39";

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);

                    break;
                case "CODE_128":
                    barcodeNewFormat = BarcodeFormat.CODE_128;
                    barCodeType = Barcode128.CODE128;
                    barCodeHeight = 400;
                    absoluteX = 50;
                    absoluteY = 300;
                    parcent = 52;

                    codeFormat = "CODE 128";

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);
                    break;
                case "UPC_A":
                    barcodeNewFormat = BarcodeFormat.UPC_A;
                    barCodeType = BarcodeEAN.UPCA;
                    barcodeNewFormat = BarcodeFormat.UPC_A;
                    barCodeHeight = 100;
                    absoluteX = 50;
                    absoluteY = 80;
                    parcent = 600;

                    codeFormat = "UPC-A";

                    barCodeGenerateEncoder(codeData, barCodeType, Color.parseColor("#00FFFFFF"), Color.BLACK, barCodeHeight,
                            absoluteX, absoluteY, parcent);

                    break;
                default:
                    //Utility.removeProgress();
                    new MaterialDialog.Builder(activity)
                            .title(R.string.appName)
                            .content(R.string.msgNoReadCodeContent)
                            .neutralText(R.string.msgOk)
                            .itemsColor(Utility.fetchPrimaryColor(activity))
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    activity.onBackPressed();
                                }
                            })
                            .cancelable(false)
                            .show();
            }
        }
    }

    private void QrGenerateEncoder(String codeData) {
        try {
            Log.e("qr generate", "yes");
            bitmap = createQrCode(codeData, charset, setHintMap(), smallestDimension(), smallestDimension(), BLACK, WHITE);

            if (bitmap != null) {
                if (Utility.qrCodeRead(activity, bitmap) != null) {
                    imgQrCode.setImageBitmap(bitmap);
                    isResult = true;
                } else {
                    isResult = false;
                }
            } else {
                imgQrCode.setImageResource(0);
            }
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace();
            Utility.logE(TAG, e.getMessage());
        }
    }

    private class LoadQRBitmap extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            bitmap = createQrCode(codeData, charset, setHintMap(), smallestDimension(), smallestDimension(), BLACK, WHITE);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Utility.showDefaultProgress(activity, getResources().getString(R.string.msgPbBarCode));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (bitmap != null) {
                if (Utility.qrCodeRead(activity, bitmap) != null) {
                    imgQrCode.setImageBitmap(bitmap);
                    isResult = true;
                } else {
                    isResult = false;
                }
            } else {
                imgQrCode.setImageResource(0);
            }
            Utility.removeProgress();

            if (isResult) {
                ParsedResult parsedResult = ResultParser.parseResult(Utility.qrCodeResult);

                if (codeData != null && codeType != null && codeFormat != null) {
                    encodeQRCodeContents(Utility.qrCodeResult.getText(), parsedResult.getType().name(),
                            Utility.qrCodeResult.getBarcodeFormat().name(), Utility.qrCodeResult);
                } else {
                    Utility.logE(TAG, "Qr code Data Null");
                }
            } else {
                new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();
                Utility.logE(TAG, "Can not read code");
            }

        }
    }

    private void setQrCodeColor(String codeData, int color, int color1) {
        try {
            bitmap = createQrCode(codeData, charset, setHintMap(), smallestDimension(), smallestDimension(), color, color1);

            if (bitmap != null) {
                imgQrCode.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, e.getMessage());
        }
    }

    private Map setHintMap() {
        Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hintMap.put(EncodeHintType.MARGIN, 0);

        return hintMap;
    }

    private int smallestDimension() {
        int width = 500, height = 500;
        return width < height ? width : height;
    }

    private void barCodeGenerateEncoder(String codeData, String codeFormat) {
        View v = new BarcodeView(activity, codeData, codeFormat);

        Bitmap srcBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        v.draw(canvas);

        relQrCode.setVisibility(View.GONE);
        imgBarCode.setImageBitmap(bitmap);
        relBarCode.setVisibility(View.VISIBLE);

    }


    /*private void barCodeGenerateEncoder(String codeData, int codeType, int backColor, int foreColor, float BarCodeHeight,
                                        float absoluteX, float absoluteY, float percent) {


        try {
            File file = Utility.getDirectoryFilePath(activity, "PDF", "sample.pdf");

            Rectangle pageSize = new Rectangle(PageSize.A4);
            pageSize.setBackgroundColor(new BaseColor(backColor));

            Document document = new Document(pageSize);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

            Image image = null;

            if (codeType == Barcode128.CODE128) {
                Barcode128 barcode128 = new Barcode128();
                barcode128.setCodeType(codeType);
                barcode128.setCode(codeData);
                image = barcode128.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));
            } else if (codeType == 4) {
                Barcode39 code39 = new Barcode39();
                code39.setCode(codeData);
                code39.setStartStopText(false);
                code39.setExtended(true);
                image = code39.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));
            } else {
                BarcodeEAN barcodeEAN = new BarcodeEAN();
                barcodeEAN.setCodeType(codeType);
                barcodeEAN.setCode(codeData);
                barcodeEAN.setBarHeight(barCodeHeight);
                image = barcodeEAN.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));
            }

            assert image != null;


            //image.setAbsolutePosition(absoluteX, (barCodeHeight-image.getHeight())/2);
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / image.getWidth()) * 100;

            image.scalePercent(scaler);

            image.setAlignment(Element.ALIGN_CENTER);
            image.setAbsolutePosition((pageSize.getWidth() - image.getScaledWidth()) / 2, (pageSize.getHeight() - image.getScaledHeight()) / 2);




           *//* image.setAbsolutePosition(absoluteX, absoluteY);
            image.scalePercent(percent);*//*

            document.add(image);
            document.close();

            Bitmap bp = getBitmap(file);

            relQrCode.setVisibility(View.GONE);

            new generateQrcode(imgBarCode, bp).execute();
            relBarCode.setVisibility(View.VISIBLE);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }*/

    private void barCodeGenerateEncoder(String codeData, int codeType, int backColor, int foreColor, float BarCodeHeight,
                                        float absoluteX, float absoluteY, float percent) {


        new generateBarcodeEncoder(codeData, codeType, backColor, foreColor).execute();


    }


    private class generateBarcodeEncoder extends AsyncTask<String, Void, Bitmap> {

        Bitmap bitmap;
        String codeData;
        int codeType, backColor, foreColor;

        public generateBarcodeEncoder(String codeData, int codeType, int backColor, int foreColor) {
            this.codeData = codeData;
            this.codeType = codeType;
            this.backColor = backColor;
            this.foreColor = foreColor;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.showDefaultProgress(activity, getResources().getString(R.string.msgPbBarCode));
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                /*File file = Utility.getDirectoryFilePath(activity, "PDF", "sample.pdf");

                Rectangle pageSize = new Rectangle(PageSize.A4);
                pageSize.setBackgroundColor(new BaseColor(backColor));

                Document document = new Document(pageSize);
                PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));

                document.open();
                PdfContentByte pdfContentByte = pdfWriter.getDirectContent();

                Image image = null;

                if (codeType == Barcode128.CODE128) {
                    Barcode128 barcode128 = new Barcode128();
                    barcode128.setCodeType(codeType);
                    barcode128.setCode(codeData);
                    image = barcode128.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));
                } else if (codeType == 4) {
                    Barcode39 code39 = new Barcode39();
                    code39.setCode(codeData);
                    code39.setStartStopText(false);
                    code39.setExtended(true);
                    image = code39.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));
                } else {
                    BarcodeEAN barcodeEAN = new BarcodeEAN();
                    barcodeEAN.setCodeType(codeType);
                    barcodeEAN.setCode(codeData);
                    barcodeEAN.setBarHeight(barCodeHeight);
                    image = barcodeEAN.createImageWithBarcode(pdfContentByte, new BaseColor(foreColor), new BaseColor(foreColor));


                }

                *//*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }*//*

                assert image != null;


                //image.setAbsolutePosition(absoluteX, (barCodeHeight-image.getHeight())/2);
                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - 0) / image.getWidth()) * 100;

                image.scalePercent(scaler);

                image.setAlignment(Element.ALIGN_CENTER);
                image.setAbsolutePosition((pageSize.getWidth() - image.getScaledWidth()) / 2, (pageSize.getHeight() - image.getScaledHeight()) / 2);

                //image.getRawData()



           *//* image.setAbsolutePosition(absoluteX, absoluteY);
            image.scalePercent(percent);*//*

                document.add(image);
                document.close();*/
                //PdfReader pdfReader =PdfReader.get


                //byte [] pdfcontent= pdfReader.getPageContent(0);


                Log.e("codeData", codeData);
                bitmap = createBarCode(codeData, charset, setHintMap(), smallestDimension(), smallestDimension(), foreColor
                        , backColor, barcodeNewFormat);

                //bitmap = getBitmap(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            Utility.removeProgress();
            if (bitmap != null) {
                relQrCode.setVisibility(View.GONE);

                new generateQrcode(imgBarCode, bitmap).execute();
                relBarCode.setVisibility(View.VISIBLE);
                setImageThumb();
                mAdapter.notifyDataSetChanged();
            } else {
                new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();
            }

        }
    }


    public Bitmap getBitmap(File file) {
        int pageNum = 0;

        /*PdfiumCore pdfiumCore = new PdfiumCore(activity);

        try {
            com.shockwave.pdfium.PdfDocument pdfDocument = pdfiumCore.newDocument(openFile(file));
            pdfiumCore.openPage(pdfDocument, pageNum);

            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum);

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setHasAlpha(true);
            pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 0, 0, width, height);

            pdfiumCore.closeDocument(pdfDocument);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        return null;
    }

    private void barCodeGenerateEncoder(String codeData, String codeFormat, int color, int color1) {
        View v = new BarcodeView(activity, codeData, codeFormat);

        Bitmap srcBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        v.draw(canvas);

       /* bitmap = getBitmapWithTransparentBG(srcBitmap, Color.WHITE, Color.TRANSPARENT);
        relQrCode.setVisibility(View.GONE);
        imgBarCode.setImageBitmap(bitmap);
        relBarCode.setVisibility(View.VISIBLE);*/
    }


    private void createBarCode(String content) {
        Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_39, 800, 200, null, true);
        relQrCode.setVisibility(View.GONE);
        imgBarCode.setImageBitmap(bitmap);
        relBarCode.setVisibility(View.VISIBLE);
    }

    private void showImageDialog() {
        if (imgQrCodeLogo.getVisibility() == View.GONE) {
            checkPermissionStorage();

            if (hasStoragePermGranted) {
                getImageFromGallery();
            }
        } else {
            new MaterialDialog.Builder(activity)
                    .title(R.string.appName)
                    .items(R.array.SelectImage)
                    .itemsColor(Utility.fetchPrimaryColor(activity))
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    setReset();
                                    break;

                                case 1:
                                    checkPermissionStorage();

                                    if (hasStoragePermGranted) {
                                        getImageFromGallery();
                                    }
                                    break;

                                default:
                                    break;
                            }
                        }

                    })
                    .show();
        }
    }

    private void setReset() {
        imgQrCodeLogo.setVisibility(View.GONE);
        frmQrCodeLogo.setVisibility(View.GONE);
    }

    private void getImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.GET_IMAGE_FROM_GALLERY);
    }

    public void setImageThumb() {
        mItemList.clear();
        if (relQrCode.getVisibility() == View.VISIBLE) {
            mItemList.add(new ImageItem(0, R.drawable.photo_library));
            mItemList.add(new ImageItem(1, R.drawable.none));
            mItemList.add(new ImageItem(2, R.drawable.qr_thumb_1));
            mItemList.add(new ImageItem(3, R.drawable.qr_thumb_2));
            mItemList.add(new ImageItem(4, R.drawable.qr_thumb_3));
            mItemList.add(new ImageItem(5, R.drawable.qr_thumb_4));
            mItemList.add(new ImageItem(6, R.drawable.qr_thumb_5));
            mItemList.add(new ImageItem(7, R.drawable.qr_thumb_6));
        } else {
            mItemList.add(new ImageItem(0, R.drawable.none));
            mItemList.add(new ImageItem(1, R.drawable.bar_code_thumb_1));
            mItemList.add(new ImageItem(2, R.drawable.bar_code_thumb_2));
            mItemList.add(new ImageItem(3, R.drawable.bar_code_thumb_3));
            mItemList.add(new ImageItem(4, R.drawable.bar_code_thumb_4));
            mItemList.add(new ImageItem(5, R.drawable.bar_code_thumb_5));
        }
    }

    private void setVisibilityGone() {
        activity.bottomNavViewEx.setVisibility(View.VISIBLE);
        imgQrCodeBg.setVisibility(View.GONE);
        frmQrCodeLogo.setVisibility(View.GONE);
        imgQrCodeLogo.setVisibility(View.GONE);
        relBarCode.setVisibility(View.GONE);
        leyBarCode.setVisibility(View.GONE);
        leyText.setVisibility(View.GONE);
        leyCall.setVisibility(View.GONE);
        leySMS.setVisibility(View.GONE);
        leyEmail.setVisibility(View.GONE);
        leyURL.setVisibility(View.GONE);
        leyDirection.setVisibility(View.GONE);
        leyWIFI.setVisibility(View.GONE);
        leyContact.setVisibility(View.GONE);
        leyEvent.setVisibility(View.GONE);
        leyFaceBook.setVisibility(View.GONE);
        leyInstaGram.setVisibility(View.GONE);
        leyWhatsApp.setVisibility(View.GONE);
        leyTwitter.setVisibility(View.GONE);
        leyLinkedIn.setVisibility(View.GONE);
        leySnapChat.setVisibility(View.GONE);
    }

    private void encodeBarCodeContents(String details, String type, String codeFormat) {
        Utility.logE(TAG, "QR Code and Bar Code Content : " + details);
        Utility.logE(TAG, "QR Code and Bar Code Scan Type : " + type);
        Utility.logE(TAG, "QR Code and Bar Code Scan Format : " + codeFormat);

        switch (type) {
            case "PRODUCT":
                /* switch (formats) {
                case "EAN_13":
                    formats = "EAN-13";
                    break;
                case "EAN_8":
                    formats = "EAN-8";
                    break;
                case "UPC_E":
                    formats = "UPC-E";
                    break;
                case "CODE_39":
                    formats = "CODE 39";
                    break;
                case "CODE_128":
                    formats = "CODE 128";
                    break;
                case "UPC_A":
                    formats = "UPC-A";
                    break;
                case "CODE_93":
                    formats = "CODE 93";
                    break;
            }*/

                formats = activity.getResources().getString(R.string.txtBarcode) + codeFormat;
                title = formats;

                if (details != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldText))
                            + details + "<br />";
                }

                leyBarCode.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                Log.e("barcode", "yes");
                checkPermissionStorage();
                break;

            case "TEXT":
                  /*switch (formats) {
                case "EAN_13":
                    formats = "EAN-13";
                    break;
                case "EAN_8":
                    formats = "EAN-8";
                    break;
                case "UPC_E":
                    formats = "UPC-E";
                    break;
                case "CODE_39":
                    formats = "CODE 39";
                    break;
                case "CODE_128":
                    formats = "CODE 128";
                    break;
                case "UPC_A":
                    formats = "UPC-A";
                    break;
                case "CODE_93":
                    formats = "CODE 93";
                    break;
            }*/

                formats = activity.getResources().getString(R.string.txtBarcode) + codeFormat;
                title = formats;

                detail = format(activity.getResources().getString(R.string.txtBoldText))
                        + details + "<br />";


                leyBarCode.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);

                break;

            case "ISBN":

                detail = "";
                formats = activity.getResources().getString(R.string.txtBarcode) + codeFormat;
                title = formats;

                detail = "<b>ISBN : </b>" + details;


                leyBarCode.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                /*new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();*/
                Utility.logE(TAG, "Can not read code");

                break;
        }

        if (type.equals("PRODUCT")) {
            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateBCR));

            txtType.setText(formats);
            txtValue.setText(getSpannedText(detail));

            if (txtValue != null) {
                //removeUnderlines((Spannable) txtValue.getText());
            }
            shareDetail = detail;
        } else if (type.equals("TEXT")) {
            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateBCR));

            txtType.setText(formats);
            txtValue.setText(getSpannedText(detail));

            if (txtValue != null) {
                //removeUnderlines((Spannable) txtValue.getText());
            }
            shareDetail = detail;
        } else {
            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateQCR));
            txtType.setText(title);
            txtValue.setText(getSpannedText(detail));
            if (txtValue != null) {
                //removeUnderlines((Spannable) txtValue.getText());
            }
            shareDetail = detail;
        }
    }

    private void encodeQRCodeContents(String details, String type, String codeFormat, Result result) {
        Utility.logE(TAG, "QR Code and Bar Code Content : " + details);
        Utility.logE(TAG, "QR Code and Bar Code Scan Type : " + type);
        Utility.logE(TAG, "QR Code and Bar Code Scan Format : " + codeFormat);


        ProductParsedResult productResult;
        String typeName;

        typeName = getUriType(type);
        //typeName = type;

        switch (typeName) {
            case "PRODUCT":
                productResult = (ProductParsedResult) ResultParser.parseResult(result);

                switch (codeFormat) {
                    case "EAN_13":
                        convertFormat = "EAN-13";
                        break;
                    case "EAN_8":
                        convertFormat = "EAN-8";
                        break;
                    case "UPC_E":
                        convertFormat = "UPC-E";
                        break;
                    case "CODE_39":
                        convertFormat = "CODE 39";
                        break;
                    case "CODE_128":
                        convertFormat = "CODE 128";
                        break;
                    case "UPC_A":
                        convertFormat = "UPC-A";
                        break;
                    case "CODE_93":
                        convertFormat = "CODE 93";
                        break;
                }

                formats = activity.getResources().getString(R.string.txtBarcode) + convertFormat;
                title = formats;

                if (productResult.getProductID() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldText))
                            + productResult.getProductID() + "<br />";
                }

                leyBarCode.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                break;

            case "TEXT":
                TextParsedResult textResult = (TextParsedResult) ResultParser.parseResult(result);

                if (codeFormat.equals("QR_CODE")) {
                    formats = "Text : " + codeFormat;
                    title = activity.getResources().getString(R.string.txtTextName);

                    if (textResult.getLanguage() != null) {
                        detail = format(activity.getResources().getString(R.string.txtBoldLanguage))
                                + textResult.getLanguage() + "<br />"
                                + format(activity.getResources().getString(R.string.txtBoldText))
                                + textResult.getText();
                    } else {
                        detail = format(activity.getResources().getString(R.string.txtBoldText))
                                + textResult.getText();
                    }

                    leyText.setVisibility(View.VISIBLE);
                    imgIcon.setImageResource(R.drawable.ic_text);
                } else {

                    switch (codeFormat) {
                        case "EAN_13":
                            convertFormat = "EAN-13";
                            break;
                        case "EAN_8":
                            convertFormat = "EAN-8";
                            break;
                        case "UPC_E":
                            convertFormat = "UPC-E";
                            break;
                        case "CODE_39":
                            convertFormat = "CODE 39";
                            break;
                        case "CODE_128":
                            convertFormat = "CODE 128";
                            break;
                        case "UPC_A":
                            convertFormat = "UPC-A";
                            break;
                        case "CODE_93":
                            convertFormat = "CODE 93";
                            break;
                    }

                    formats = "Bar Code : " + convertFormat;
                    title = formats;
                    detail = format(activity.getResources().getString(R.string.txtBoldText))
                            + textResult.getText();

                    leyBarCode.setVisibility(View.VISIBLE);
                    imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                }
                break;

            case "TEL":
                TelParsedResult telResult = (TelParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtPhoneNumName);

                if (telResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + telResult.getTitle() + "<br />";
                }

                detail = format(activity.getResources().getString(R.string.txtBoldPhoneNum))
                        + telResult.getNumber();

                leyCall.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_telephone);
                break;

            case "SMS":
                SMSParsedResult smsResult = (SMSParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtSmsName);

                if (smsResult.getNumbers() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldNumber))
                            + Arrays.toString(smsResult.getNumbers()).replace("[", "").replace("]", "") + "<br />";
                }

                if (smsResult.getSubject() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldSub))
                            + smsResult.getSubject() + "<br />";
                }

                if (smsResult.getBody() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldMsg))
                            + smsResult.getBody() + "<br />";
                }

                leySMS.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_sms);
                break;

            case "EMAIL_ADDRESS":
                EmailAddressParsedResult emailResult = (EmailAddressParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtEmailName);

                detail = "";

                if (emailResult.getEmailAddress() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTo))
                            + emailResult.getEmailAddress() + "<br />";
                }

                if (emailResult.getSubject() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldSub))
                            + emailResult.getSubject() + "<br />";
                }

                if (emailResult.getBody() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldMsg))
                            + emailResult.getBody();
                }

                leyEmail.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_mail);
                break;

            case "URI":
                URIParsedResult uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtWebSiteName);

                try {
                    url = new URL(uriResult.getURI());
                    Utility.logE(TAG, "url description : " + url.getUserInfo() + " ");
                    Utility.logE(TAG, "url description : " + url.getRef() + " ");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle() + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldURL))
                            + uriResult.getURI();
                }

                leyURL.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_web);
                break;

            case "GEO":
                GeoParsedResult resultGeo = (GeoParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtLocationName);

                if (resultGeo.getGeoURI() != null) {
                    //detail = "GeoURI : " + resultGeo.getGeoURI() + "<br />";
                }

                detail = /*detail +*/ format(activity.getResources().getString(R.string.txtBoldLatitude)) + resultGeo.getLatitude() + "<br />";
                detail = detail + format(activity.getResources().getString(R.string.txtBoldLongitude)) + resultGeo.getLongitude();

                leyDirection.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_location);
                break;

            case "WIFI":
                WifiParsedResult wifiResult = (WifiParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtWifiName);

                if (wifiResult.getSsid() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldNetWorkName))
                            + wifiResult.getSsid() + "<br />";

                    wifiName = format(activity.getResources().getString(R.string.txtBoldNetWorkName))
                            + wifiResult.getSsid();
                }

                if (wifiResult.getPassword() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldPass))
                            + wifiResult.getPassword() + "<br />";

                    wifiPass = wifiResult.getPassword();
                }

                if (wifiResult.getNetworkEncryption() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldEncryption))
                            + wifiResult.getNetworkEncryption();

                    wifiNoEncryption = format(activity.getResources().getString(R.string.txtBoldEncryption))
                            + wifiResult.getNetworkEncryption();
                }

                leyWIFI.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_wifi);
                break;

            case "ADDRESSBOOK":
                AddressBookParsedResult addressResult = (AddressBookParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtVCardName);

                if (addressResult.getNames() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldName))
                            + Arrays.toString(addressResult.getNames()).replace("[", "").replace("]", "")
                            + "<br />";
                }

                if (addressResult.getEmails() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldEmail))
                            + Arrays.toString(addressResult.getEmails()).replace("[", "").replace("]", "")
                            + "<br />";
                }

                if (addressResult.getPhoneNumbers() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldPhoneNum))
                            + Arrays.toString(addressResult.getPhoneNumbers()).replace("[", "").replace("]", "")
                            + "<br />";
                }

                if (addressResult.getAddresses() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldCity))
                            + Arrays.toString(addressResult.getAddresses()).replace("[", "").replace("]", "")
                            + "<br />";
                }

                if (addressResult.getOrg() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldCompanyName))
                            + addressResult.getOrg() +
                            "<br />";
                }

                if (addressResult.getBirthday() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldBirthDay))
                            + addressResult.getBirthday()
                            + "<br />";
                }

                if (addressResult.getInstantMessenger() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldInstantMessenger))
                            + addressResult.getInstantMessenger()
                            + "<br />";
                }

                if (addressResult.getNote() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldNote))
                            + addressResult.getNote()
                            + "<br />";
                }

                if (addressResult.getTitle() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldTitle))
                            + addressResult.getTitle()
                            + "<br />";
                }

                leyContact.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_vcard);
                break;

            case "CALENDAR":
                CalendarParsedResult calendarResult = (CalendarParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtEventName);

                if (calendarResult.getSummary() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldSummary))
                            + calendarResult.getSummary()
                            + "<br />";
                }

                if (calendarResult.getDescription() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldDesc))
                            + calendarResult.getDescription()
                            + "<br />";
                }

                if (calendarResult.getLocation() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldLocation))
                            + calendarResult.getLocation()
                            + "<br />";
                }

                if (calendarResult.getOrganizer() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldOrganizer))
                            + calendarResult.getOrganizer()
                            + "<br />";
                }

                if (calendarResult.getStart() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldStart))
                            + calendarResult.getStart()
                            + "<br />";
                }

                if (calendarResult.getEnd() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldEnd))
                            + calendarResult.getEnd()
                            + "<br />";
                }

                if (calendarResult.getAttendees() != null) {
                    detail = detail + format(activity.getResources().getString(R.string.txtBoldAttendees))
                            + Arrays.toString(calendarResult.getAttendees()).replace("[", "").replace("]", "")
                            + "<br />";
                }

                leyEvent.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_calendar);
                break;

            case "FACEBOOK":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtFacebookName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldFacebook))
                            + uriResult.getURI();
                }

                leyFaceBook.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_facebook);
                break;

            case "INSTAGRAM":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtInstagramName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldInstagram))
                            + uriResult.getURI();
                }

                leyInstaGram.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.mipmap.ic_instagram);
                break;

            case "WHATSAPP":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtWhatsAppName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldWhatsApp))
                            + uriResult.getURI();
                }

                leyWhatsApp.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_whatsapp);
                break;

            case "TWITTER":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtTwitterName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTwitter))
                            + uriResult.getURI();
                }

                leyTwitter.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_twitter);
                break;

            case "LINKEDIN":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtLinkedInName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldLinkedIn))
                            + uriResult.getURI();
                }

                leyLinkedIn.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_linked_in);
                break;

            case "SNAPCHAT":
                uriResult = (URIParsedResult) ResultParser.parseResult(result);

                formats = "QR Code : " + codeFormat;
                title = activity.getResources().getString(R.string.txtSnapChatName);

                if (uriResult.getTitle() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldTitle))
                            + uriResult.getTitle()
                            + "<br />";
                }

                if (uriResult.getURI() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldSnapChat))
                            + uriResult.getURI();
                }

                leySnapChat.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_snapchat);
                break;

            case "ISBN":

                detail = "";


                ISBNParsedResult ISBNResult = (ISBNParsedResult) ResultParser.parseResult(result);

                if (ISBNResult.getISBN() != null)
                    detail = "<b>ISBN :</b> " + ISBNResult.getISBN() + "\n";


                switch (codeFormat) {
                    case "EAN_13":
                        convertFormat = "EAN-13";
                        break;
                    case "EAN_8":
                        convertFormat = "EAN-8";
                        break;
                    case "UPC_E":
                        convertFormat = "UPC-E";
                        break;
                    case "CODE_39":
                        convertFormat = "CODE 39";
                        break;
                    case "CODE_128":
                        convertFormat = "CODE 128";
                        break;
                    case "UPC_A":
                        convertFormat = "UPC-A";
                        break;
                    case "CODE_93":
                        convertFormat = "CODE 93";
                        break;
                }

                formats = activity.getResources().getString(R.string.txtBarcode) + convertFormat;
                title = formats;

                /*if (productResult.getProductID() != null) {
                    detail = format(activity.getResources().getString(R.string.txtBoldText))
                            + productResult.getProductID() + "<br />";
                }
*/
                leyBarCode.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                /*new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();*/
                Utility.logE(TAG, "Can not read code");

                break;
        }

        if (typeName.equals("PRODUCT")) {
            Log.e("TAG_", "PRODUCT");
            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateBCR));
            txtType.setText(formats);
            //txtValue.setTypeface(txtValue.getTypeface(), Typeface.BOLD);
            txtValue.setText(getSpannedText(detail));

            if (txtValue != null) {
//                removeUnderlines((Spannable) txtValue.getText());
            }
            shareDetail = detail;
        } else if (typeName.equals("TEXT")) {
            Log.e("TAG_", "TEXT");
            if (codeFormat.equals("QR_CODE")) {
                Log.e("TAG_", "QR_CODE");
                txtAppName.setText(activity.getResources().getString(R.string.txtAppNameCreateQCR));
                txtType.setText(title);
//                txtValue.setTypeface(txtValue.getTypeface(), Typeface.BOLD);
                txtValue.setText(getSpannedText(detail));

                if (txtValue != null) {
//                    removeUnderlines((Spannable) txtValue.getText());
                }
                shareDetail = detail;
            } else {
                txtAppName.setText(getResources().getString(R.string.txtAppNameCreateBCR));
                txtType.setText(formats);
                txtValue.setTypeface(txtValue.getTypeface(), Typeface.BOLD);
                txtValue.setText(getSpannedText(detail));

                if (txtValue != null) {
                    //removeUnderlines((Spannable) txtValue.getText());
                }
                shareDetail = detail;
            }
        } else if (typeName.equals("WIFI")) {
            Log.e("TAG_", "WIFI");

            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateQCR));
            txtType.setText(title);
            txtValue.setVisibility(View.GONE);
            txtValue.setText(getSpannedText(detail));

            txtValueWifi1.setVisibility(View.VISIBLE);
            if (wifiPass==null)
                wifiPass="";
            if ( wifiPass.equals("")) {
                txtValueWifi.setVisibility(View.GONE);
                txtValueWifi2.setVisibility(View.GONE);
                leyPassWord.setVisibility(View.GONE);

            } else {
                txtValueWifi.setVisibility(View.VISIBLE);
                txtValueWifi2.setVisibility(View.VISIBLE);
                leyPassWord.setVisibility(View.VISIBLE);
            }

            txtValueWifi2.setVisibility(View.VISIBLE);
            txtValueWifi3.setVisibility(View.VISIBLE);
            txtValueWifi1.setText(getSpannedText(wifiName));
            txtValueWifi2.setText(getSpannedText(wifiPass));
            txtValueWifi3.setText(getSpannedText(wifiNoEncryption));

            if (txtValue != null) {
                //removeUnderlines((Spannable) txtValue.getText());
            }
            shareDetail = detail;
        } else {
            txtAppName.setText(getResources().getString(R.string.txtAppNameCreateQCR));
            txtType.setText(title);
            //  txtValue.setTypeface(txtValue.getTypeface(), Typeface.BOLD);
            txtValue.setText(getSpannedText(detail));

            if (txtValue != null) {
//                removeUnderlines((Spannable) txtValue.getText());
            }

            shareDetail = detail;
        }

        if (scanType != null) {
            if (scanType.equals("ScanResultData"))
                if (Utility.isNewResult) {
                    Utility.logE(TAG, "Add in Scan Result Data");

                    Gson gson = new Gson();

                    historyTable = new HistoryTable(typeName, details, codeFormat, Utility.getCurrantTimeStamp(), gson.toJson(Utility.qrCodeResult).getBytes());

                    Query.historySave(historyTable);
                    Query.historyShow();

                    Utility.isNewResult = false;
                }
        } else {
            Utility.logE(TAG, "Scan Result Data Not Found");
        }
    }

    private String getUriType(String type) {

        int checkHost = 0;

        if (type.equals("URI")) {

            URIParsedResult uriResult = (URIParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            String faceBook, instagram, whatsApp, twitter, linkedIn, snapChat;

            faceBook = "www.facebook.com";
            instagram = "www.instagram.com";
            whatsApp = "wa.me";
            twitter = "twitter.com";
            linkedIn = "www.linkedin.com";
            snapChat = "www.snapchat.com";

            try {
                url = new URL(uriResult.getURI());
                Log.e("url description", url.getUserInfo() + " ");
                Log.e("url description", url.getRef() + " ");
                Log.e("url description", url.getProtocol() + " ");
                Log.e("url description", url.getHost() + " ");

                checkHost = 0;
            } catch (MalformedURLException e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());

                checkHost = 1;
            }

            if (checkHost == 0) {
                if (faceBook.equals(url.getHost())) {
                    type = "FACEBOOK";
                    Log.e("TAG_", "Type : " + type);

                    return "FACEBOOK";
                } else if (instagram.equals(url.getHost())) {
                    type = "INSTAGRAM";
                    Log.e("TAG_", "Type : " + type);

                    return "INSTAGRAM";
                } else if (whatsApp.equals(url.getHost())) {
                    type = "WHATSAPP";
                    Log.e("TAG_", "Type : " + type);

                    return "WHATSAPP";
                } else if (twitter.equals(url.getHost())) {
                    type = "TWITTER";
                    Log.e("TAG_", "Type : " + type);

                    return "TWITTER";
                } else if (linkedIn.equals(url.getHost())) {
                    type = "LINKEDIN";
                    Log.e("TAG_", "Type : " + type);

                    return "LINKEDIN";
                } else if (snapChat.equals(url.getHost())) {
                    type = "SNAPCHAT";
                    Log.e("TAG_", "Type : " + type);

                    return "SNAPCHAT";
                } else if (snapChat.equals(url.getHost())) {
                    type = "SNAPCHAT";
                    Log.e("TAG_", "Type : " + type);

                    return "SNAPCHAT";
                } else {
                    Log.e("TAG_", "Type : " + type);

                    return type;
                }
            } else {

            }
        }
        return type;
    }

    private Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }

    @SuppressLint("ResourceAsColor")
    public Bitmap createQrCode(String contents, String charset, Map hintMap, int qrWidth, int qrHeight, int Color, int Color1) {
        BitMatrix bitMatrix;
        Bitmap bitmap = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(new String(contents.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrWidth, qrHeight, hintMap);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color : Color1;
                }
            }

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace();

            return null;
        }

        return bitmap;
    }

    @SuppressLint("ResourceAsColor")
    public Bitmap createBarCode(String contents, String charset, Map hintMap, int qrWidth, int qrHeight, int Color, int Color1, BarcodeFormat barcodeFormat) {
        BitMatrix bitMatrix;
        Bitmap bitmap = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(new String(contents.getBytes(charset), charset),
                    barcodeFormat, qrWidth, qrHeight, hintMap);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? Color : Color1;
                }
            }

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace();

            return null;
        }

        return bitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            imgQrCodeLogo.setVisibility(View.GONE);
            frmQrCodeLogo.setVisibility(View.GONE);
            return;
        }

        switch (requestCode) {
            case Constant.GET_IMAGE_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    try {
                        imgQrCodeLogo.setVisibility(View.VISIBLE);
                        frmQrCodeLogo.setVisibility(View.VISIBLE);

                        Uri galleryImageUri = data.getData();

                        assert galleryImageUri != null;
                        activity.getContentResolver().notifyChange(galleryImageUri, null);

                        String filePath = CompressImageHelper.compressImage(activity, galleryImageUri);
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        imgQrCodeLogo.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.getMessage();
                        Utility.logE(TAG, e.getMessage() + " ");
                    }
                }
                break;

            default:
                break;
        }
    }

    private class BarCodeBitmapThread extends Thread {

        private Bitmap bitmap;

        public BarCodeBitmapThread(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            bitmap = getBitmapWithTransparentBG(bitmap, Color.WHITE, Color.TRANSPARENT);
            handler.sendMessage(handler.obtainMessage());
        }

        public Bitmap getBitmapWithTransparentBG(Bitmap srcBitmap, int fromBgColor, int toBgColor) {
            Bitmap result = srcBitmap.copy(Bitmap.Config.ARGB_8888, true);
            int nWidth = result.getWidth();
            int nHeight = result.getHeight();
            for (int y = 0; y < nHeight; ++y)
                for (int x = 0; x < nWidth; ++x) {
                    int nPixelColor = result.getPixel(x, y);
                    if (nPixelColor >= fromBgColor && nPixelColor <= toBgColor)
                        result.setPixel(x, y, Color.TRANSPARENT);
                }
            return result;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }

    private class qrCodeBitmapThread extends Thread {

        private Bitmap bitmap;

        public qrCodeBitmapThread(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            try {
                qrCodeRead(activity, bitmap);
            } catch (Exception e) {
                e.getMessage();
            }
            handler.sendMessage(handler.obtainMessage());
        }
    }

    private class generateQrcode extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        Bitmap bitmap;

        public generateQrcode(ImageView bmImage, Bitmap bitmap) {
            this.bmImage = bmImage;
            this.bitmap = bitmap;
            bmImage.setVisibility(View.GONE);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.showDefaultProgress(activity, activity.getResources().getString(R.string.msgPbBarCode));
        }

        protected Bitmap doInBackground(String... urls) {
            this.bitmap.copy(Bitmap.Config.ARGB_8888, true);
            int nWidth = bitmap.getWidth();
            int nHeight = bitmap.getHeight();
            for (int y = 0; y < nHeight; ++y)
                for (int x = 0; x < nWidth; ++x) {
                    int nPixelColor = bitmap.getPixel(x, y);
                    if (nPixelColor >= Color.WHITE && nPixelColor <= Color.TRANSPARENT)
                        bitmap.setPixel(x, y, Color.TRANSPARENT);
                }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setVisibility(View.VISIBLE);
            bmImage.setImageBitmap(result);
            Utility.removeProgress();

            if (codeData != null && codeType != null && codeFormat != null) {
                //encodeBarCodeContents(codeData, codeType, codeFormat);
            } else {
                //Utility.logE(TAG, "Barcode Data Null");
                //Utility.removeProgress();
                new MaterialDialog.Builder(activity)
                        .title(R.string.appName)
                        .content(R.string.msgNoReadCodeContent)
                        .neutralText(R.string.msgOk)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                activity.onBackPressed();
                            }
                        })
                        .cancelable(false)
                        .show();
            }

        }
    }

}
