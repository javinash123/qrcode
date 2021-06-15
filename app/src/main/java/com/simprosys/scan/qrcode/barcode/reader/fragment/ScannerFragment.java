package com.simprosys.scan.qrcode.barcode.reader.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.model.Theme;
import com.simprosys.scan.qrcode.barcode.reader.utility.CompressImageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Constant;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import barcodescanner.core.IViewFinder;
import barcodescanner.core.ViewFinderView;
import barcodescanner.core.ZXingScannerView;
import barcodescanner.core.ZXingScannerView.ResultHandler;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment implements ResultHandler {
    private static final int REQ_CODE_STORAGE_PERM = 1;
    private static final int REQ_CODE_CAMERA_PERM = 2;
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";
    private static final String SELECTED_FORMATS = "SELECTED_FORMATS";
    private static final String CAMERA_ID = "CAMERA_ID";

    private static ScannerFragment instance = null;

    public Boolean scanFlag = false;
    public Boolean hasCameraPermGranted = false;
    public Boolean hasStoragePermGranted = false;

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.relPlaceHolder)
    RelativeLayout relPlaceHolder;

    @BindView(R.id.imgFlash)
    ImageView imgFlash;

    @BindView(R.id.imgDivider)
    ImageView imgDivider;

    @BindView(R.id.imgGallery)
    ImageView imgGallery;

    private ZXingScannerView mScannerView;
    private ArrayList<Integer> mSelectedIndices;
    private IViewFinder mViewFinderView;
    private ViewFinderView viewFinderView;
    private ArrayList<Theme> mItemList;
    private HomeActivity activity;

    private boolean mFlash;
    private boolean mAutoFocus;
    private boolean mTouchFocus;
    private boolean mVibrate;
    private boolean mInvertScan;
    private boolean mBeep;
    private boolean mCopy;

    private int mCameraId = -1;

    private Class TAG = ScannerFragment.class;


    public ScannerFragment() {

    }

    public static ScannerFragment getInstance() {
        return instance;
    }

    public static ScannerFragment newInstance() {
        Bundle args = new Bundle();
        ScannerFragment fragment = new ScannerFragment();
        args.putString("NAME", "Scanner");
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.imgFlash)
    public void onClickFlash() {
        if (!Utility.stopClick())
            return;
        mFlash = !mFlash;
        if (mFlash) {
            imgFlash.setImageResource(R.drawable.ic_flash_off_black_24dp);
        } else {
            imgFlash.setImageResource(R.drawable.ic_flash_on_black_24dp);
        }
        mScannerView.setFlash(mFlash);
    }

    @OnClick(R.id.imgGallery)
    public void onClickGallery() {
        if (!Utility.stopClick())
            return;
        checkPermissionStorage();

        if (hasStoragePermGranted) {
            getImageQR();
        }
    }

    private void getImageQR() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constant.GET_IMAGE_QR_FROM_GALLERY);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scanner, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgFlash.setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);
        imgDivider.setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);
        imgGallery.setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);

        if (savedInstanceState != null) {
            mFlash = savedInstanceState.getBoolean(FLASH_STATE, false);
            mAutoFocus = savedInstanceState.getBoolean(AUTO_FOCUS_STATE, true);
            mSelectedIndices = savedInstanceState.getIntegerArrayList(SELECTED_FORMATS);
            mCameraId = savedInstanceState.getInt(CAMERA_ID, -1);
        } else {
            mFlash = false;
            mAutoFocus = true;
            mSelectedIndices = null;
            mCameraId = -1;
        }

        setupFormats();

        mScannerView = new ZXingScannerView(getActivity());
        container.addView(mScannerView);

        //checkPermissionCamera();

        SetSettingScan();
    }

    public void checkPermissionStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                final String[] LOCATION_PERMS = {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
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

    public void checkPermissionCamera() {
        Log.e("permission","yes");
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermission(android.Manifest.permission.CAMERA) || !hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                final String[] CAMERA_PERMS = {
                        android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                hasCameraPermGranted = false;

                requestPermissions(CAMERA_PERMS, REQ_CODE_CAMERA_PERM);
            } else {
                hasCameraPermGranted = true;
            }
        } else {
            hasCameraPermGranted = true;
        }
    }

    public boolean checkSelfPermissionCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermission(android.Manifest.permission.CAMERA) || !hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                final String[] CAMERA_PERMS = {
                        android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                //requestPermissions(CAMERA_PERMS, REQ_CODE_CAMERA_PERM);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
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

            case REQ_CODE_CAMERA_PERM:
                if (hasPermission(android.Manifest.permission.CAMERA) && hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    hasCameraPermGranted = true;
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                    showDialogPermission();
                    /*showDialogOK("Service Permissions are required for this app",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            *//*ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);*//*

                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                            startActivity(intent);

                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            dialog.dismiss();
                                            break;
                                    }
                                }
                            });*/
                } else {
                    hasCameraPermGranted = false;
                    showDialogPermissionExplain();
                    //explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                }
                break;
        }
    }

    private void showDialogPermissionExplain() {
        new MaterialDialog.Builder(activity)
                .title(R.string.appName)
                .content(R.string.msgPermissionExplainContent)
                .positiveText(R.string.msgPermissionOk)
                .negativeText(R.string.msgPermissionCancel)
                .itemsColor(Utility.fetchPrimaryColor(activity))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showDialogPermission() {
        new MaterialDialog.Builder(activity)
                .title(R.string.appName)
                .content(R.string.msgPermissionContent)
                .positiveText(R.string.msgPermissionOk)
                .negativeText(R.string.msgPermissionCancel)
                .itemsColor(Utility.fetchPrimaryColor(activity))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        activity.startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(activity);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);

                        /*Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        ScannerFragment.this.startActivity(intent);*/
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // activity.finish();
                    }
                });
        dialog.show();
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<>();
        if (mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<>();
            for (int i = 0; i < ZXingScannerView.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for (int index : mSelectedIndices) {
            formats.add(ZXingScannerView.ALL_FORMATS.get(index));
        }

        if (mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

    public void SetSettingScan() {
        mAutoFocus = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "UseAutoFocus", true);
        mTouchFocus = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "TouchFocus", true);
        mVibrate = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "Vibrate", true);
        mInvertScan = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "InvertScan", true);
        mBeep = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "Beep", true);
        mCopy = SharedPreferenceHelper.getSharedPreferenceBoolean(activity, "CopyToBoard", true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (!isVisible())
            return;*/

        activity.bottomNavViewEx.setVisibility(View.VISIBLE);
        //if (activity.bottomNavViewEx.getCurrentItem() == 0)
            mScannerView.resumeCameraPreview(this);


        if (checkSelfPermissionCamera()) {
            relPlaceHolder.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            scanConfig();
        } else {

            relPlaceHolder.setVisibility(View.VISIBLE);
            container.setVisibility(View.GONE);

            relPlaceHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermissionCamera();
                }
            });
        }
        Log.e("scan fragment", "on resume" + isVisible());
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if (!isVisible())
            return;*/
        Log.e("scan fragment", "on pause" + isVisible());
        //if (activity.bottomNavViewEx.getCurrentItem() == 0)
            mScannerView.stopCamera();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);
        outState.putIntegerArrayList(SELECTED_FORMATS, mSelectedIndices);
        outState.putInt(CAMERA_ID, mCameraId);
    }

    private void scanConfig() {
        mScannerView.stopCamera();
        setupLayout();
        scanFlag = true;
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(mCameraId);
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }

    public void setupLayout() {
        mViewFinderView = createViewFinderView(activity);
        if (mViewFinderView instanceof View) {
            mScannerView.addView((View) mViewFinderView);
        } else {
            throw new IllegalArgumentException("IViewFinder object returned by "
                    + "'createViewFinderView()' should be instance of android.view.View");
        }
    }

    protected IViewFinder createViewFinderView(Context context) {
        return new ViewFinderView(context);
    }

    @Override
    public void handleResult(Result result) {


        if (!scanFlag) {
            mScannerView.stopCamera();
            mScannerView.startCamera();

            return;
        }

        try {
            if (mBeep) {
                final MediaPlayer mPlayer = new MediaPlayer();

                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.reset();
                }

                try {
                    AssetFileDescriptor mFileDescriptor;
                    mFileDescriptor = getActivity().getAssets().openFd("beep.mp3");
                    mPlayer.setDataSource(mFileDescriptor.getFileDescriptor(), mFileDescriptor.getStartOffset(), mFileDescriptor.getLength());
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Utility.logE(TAG, e.getMessage() + " ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Utility.logE(TAG, e.getMessage() + " ");
                }
            }

            if (mVibrate) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                assert vibrator != null;
                vibrator.vibrate(100);
            }

            if (mCopy) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Code Copied", result.getText());
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(clipData);

                //Utility.setToastMessage(activity, activity.getResources().getString(R.string.txtCopyClipboard), 0);
            }

            Utility.qrCodeResult = result;
            Utility.isNewResult = true;

            Fragment fragment = CreateQRCodeResultFragment.newInstance("ScanResultData");

            activity.pushFragment(fragment);

        } catch (Exception e) {
            e.printStackTrace();
            Utility.logE(TAG, e.getMessage() + " ");

            Utility.setToastMessage(activity, "Not Supported QR or BarCode", 0);
        }

        Utility.logE(TAG, "Scan Result Contents : " + result.getText());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.GET_IMAGE_QR_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri galleryImageUri = data.getData();
                        assert galleryImageUri != null;
                        activity.getContentResolver().notifyChange(galleryImageUri, null);

                        String imgFilePath = CompressImageHelper.compressImage(activity, galleryImageUri);
                        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);

                        if (Utility.imageQrRead(activity, bitmap) != null) {
                            Utility.imageQrRead(activity, bitmap);
                        } else {
                         /*   new MaterialDialog.Builder(activity)
                                    .title(R.string.appName)
                                    .content(R.string.msgNoReadCodeContent)
                                    .neutralText(R.string.msgOk)
                                    .itemsColor(Utility.fetchPrimaryColor(activity))
                                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                            Utility.logE(TAG, "Can not read code");*/

                            Toast.makeText(activity, activity.getResources().getString(R.string.msgNoReadCodeContent), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Utility.logE(TAG, e.getMessage() + " ");
                    }
                }
                break;

            default:
                break;
        }
    }
}
